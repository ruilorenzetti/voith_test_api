package org.openapitools.service;

import com.google.gson.*;
import okhttp3.*;
import org.openapitools.model.*;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import lombok.extern.slf4j.Slf4j;

import java.io.IOException;
import java.math.BigDecimal;
import java.time.OffsetDateTime;
import java.time.format.DateTimeParseException;
import java.util.ArrayList;
import java.util.List;

@Slf4j
@Service
public class InfluxDBService {

    private static final MediaType JSON = MediaType.get("application/json");

    private final String influxdbUrl;
    private final String influxdbToken;
    private final String influxdbOrgId;
    private final String influxdbBucket;
    private final OkHttpClient client = new OkHttpClient();
    private final Gson gson = new Gson();

    public InfluxDBService(
            @Value("${influxdb.url}") String influxdbUrl,
            @Value("${influxdb.token}") String influxdbToken,
            @Value("${influxdb.org}") String influxdbOrgId,
            @Value("${influxdb.bucket}") String influxdbBucket) {
        this.influxdbUrl = influxdbUrl;
        this.influxdbToken = influxdbToken;
        this.influxdbOrgId = influxdbOrgId;
        this.influxdbBucket = influxdbBucket;
    }

    /**
     * Retorna os dados de telemetria do InfluxDB dentro do intervalo de tempo fornecido.
     */
    public List<TelemetryData> getTelemetryData(OffsetDateTime start, OffsetDateTime end) {
        return queryInfluxDB("telemetry", start, end, TelemetryData.class);
    }

    /**
     * Retorna os dados de falhas dentro do intervalo de tempo fornecido.
     */
    public List<FailureData> getFailures(OffsetDateTime start, OffsetDateTime end) {
        return queryInfluxDB("failure", start, end, FailureData.class);
    }

    /**
     * Retorna os dados de erros dentro do intervalo de tempo fornecido.
     */
    public List<ErrorData> getErrors(OffsetDateTime start, OffsetDateTime end) {
        return queryInfluxDB("error", start, end, ErrorData.class);
    }

    /**
     * Retorna os dados de manutenção dentro do intervalo de tempo fornecido.
     */
    public List<MaintenanceData> getMaintenanceRecords(OffsetDateTime start, OffsetDateTime end) {
        return queryInfluxDB("maintenance", start, end, MaintenanceData.class);
    }

    /**
     * Retorna os dados das máquinas dentro do intervalo de tempo fornecido.
     */
    public List<MachineData> getAllMachines() {
        return queryInfluxDB("machine", null, null, MachineData.class);
    }

    /**
     * Retorna os IDs únicos das máquinas armazenadas no InfluxDB.
     */
    public List<Integer> getMachineIds() {
        String query = "from(bucket: \"" + influxdbBucket + "\") " +
                "|> range(start: -30d) " +
                "|> filter(fn: (r) => r._measurement == \"machine_data\") " +
                "|> keep(columns: [\"machineId\"]) " +
                "|> distinct(column: \"machineId\")";

        List<JsonElement> records = executeQuery(query);
        List<Integer> machineIds = new ArrayList<>();

        for (JsonElement json : records) {
            Integer id = getSafeInt(json, "machineId");
            if (id != null) {
                machineIds.add(id);
            }
        }

        log.info("✅ Encontrados {} IDs únicos de máquinas.", machineIds.size());
        return machineIds;
    }

    /**
     * Método genérico para consultas ao InfluxDB via HTTP.
     */
    private <T> List<T> queryInfluxDB(String measurement, OffsetDateTime start, OffsetDateTime end, Class<T> type) {
        String timeRange = buildTimeRange(start, end);
        String query = "from(bucket:\"" + influxdbBucket + "\") " +
                timeRange + " " +
                "|> filter(fn: (r) => r._measurement == \"" + measurement + "\")";

        List<JsonElement> records = executeQuery(query);
        List<T> resultList = new ArrayList<>();

        for (JsonElement json : records) {
            T data = mapJsonToModel(json, type);
            if (data != null) {
                resultList.add(data);
            }
        }

        log.info("✅ Encontrados {} registros para '{}'.", resultList.size(), measurement);
        return resultList;
    }


    /**
     * Executa uma query no InfluxDB via HTTP e retorna uma lista de JSONs extraídos da resposta.
     */
    private List<JsonElement> executeQuery(String query) {
        List<JsonElement> records = new ArrayList<>();

        JsonObject jsonBody = new JsonObject();
        jsonBody.addProperty("query", query);
        jsonBody.addProperty("type", "flux");

        RequestBody body = RequestBody.create(jsonBody.toString(), JSON);
        Request request = new Request.Builder()
                .url(influxdbUrl + "/api/v2/query?org=" + influxdbOrgId)
                .header("Authorization", "Token " + influxdbToken)
                .header("Content-Type", "application/json")
                .post(body)
                .build();

        try (Response response = client.newCall(request).execute()) {
            if (!response.isSuccessful()) {
                log.error("❌ Erro na consulta do InfluxDB: {}", response.body().string());
                return records;
            }

            String responseBody = response.body().string();
            JsonArray jsonArray = extractJsonArray(responseBody);
            log.info("✅ Resposta recebida do InfluxDB.");
            return jsonArray.asList();

        } catch (IOException e) {
            log.error("❌ Erro ao conectar ao InfluxDB: {}", e.getMessage());
            return records;
        }
    }

    public static JsonArray extractJsonArray(String csvData) {
        // Dividir as linhas
        String[] lines = csvData.split("\n");

        // Criar um JsonArray para armazenar os objetos JSON resultantes
        JsonArray jsonArray = new JsonArray();

        // Percorrer as linhas (ignorando o cabeçalho)
        for (int i = 1; i < lines.length; i++) {
            String[] columns = lines[i].split(",(?=(?:[^\"]*\"[^\"]*\")*[^\"]*$)"); // Divide sem afetar JSONs internos

            if (columns.length < 9) continue; // Ignora linhas inválidas

            // Pega apenas o campo _value e remove aspas extras
            String jsonValue = columns[6].replaceAll("^\"|\"$", "").replace("\"\"", "\"");

            try {
                // Converte a string JSON para JsonObject e adiciona ao JsonArray
                JsonObject jsonObject = JsonParser.parseString(jsonValue).getAsJsonObject();
                jsonArray.add(jsonObject);
            } catch (Exception e) {
                System.err.println("Erro ao converter JSON: " + e.getMessage());
            }
        }

        return jsonArray;
    }

    /**
     * Processa a resposta do InfluxDB e extrai JSONs dos registros.
     */
    private List<JsonObject> parseInfluxResponse(String responseBody) {
        List<JsonObject> records = new ArrayList<>();

        String[] lines = responseBody.split("\n");
        for (String line : lines) {
            if (line.startsWith(",result,table")) {
                continue;
            }

            String[] columns = line.split(",");
            if (columns.length > 6) {
                String value = columns[5];

                try {
                    // Se for um JSON válido, converter
                    if (value.startsWith("{") && value.endsWith("}")) {
                        JsonObject json = gson.fromJson(value, JsonObject.class);
                        records.add(json);
                    }
                    // Se for um timestamp ou string comum, armazenar como um campo JSON genérico
                    else {
                        JsonObject json = new JsonObject();
                        json.addProperty("value", value);
                        records.add(json);
                    }

                } catch (JsonSyntaxException e) {
                    log.error("❌ Erro ao converter _value: {} | Erro: {}", value, e.getMessage());
                }
            }
        }
        log.info("✅ Encontrados {} registros para 'telemetry'.", records.size());
        return records;
    }

    /**
     * Converte um JSON bruto em um modelo específico.
     */
    /**
     * Converte um JSON bruto em um modelo específico.
     */
    private <T> T mapJsonToModel(JsonElement json, Class<T> type) {
        try {
            if (json != null && json.isJsonObject()) { // Verifica se o JSON é um objeto válido
                JsonObject jsonObject = json.getAsJsonObject();

                if (type == TelemetryData.class && jsonObject.size() == 6) {
                    return type.cast(new TelemetryData()
                            .machineId(getSafeInt(jsonObject, "machineId"))
                            .voltage(getSafeBigDecimal(jsonObject, "voltage"))
                            .rotation(getSafeBigDecimal(jsonObject, "rotation"))
                            .pressure(getSafeBigDecimal(jsonObject, "pressure"))
                            .vibration(getSafeBigDecimal(jsonObject, "vibration"))
                            .timestamp(parseSafeDateTime(getSafeString(jsonObject, "timestamp"))));
                }

                if (type == ErrorData.class) {
                    return type.cast(new ErrorData()
                            .machineId(getSafeInt(jsonObject, "machineId"))
                            .errorType(getSafeString(jsonObject, "errorType"))
                            .timestamp(parseSafeDateTime(getSafeString(jsonObject, "timestamp"))));
                }

                if (type == FailureData.class) {
                    return type.cast(new FailureData()
                            .machineId(getSafeInt(jsonObject, "machineId"))
                            .failureType(getSafeString(jsonObject, "failureType"))
                            .timestamp(parseSafeDateTime(getSafeString(jsonObject, "timestamp"))));
                }
                if (type == MaintenanceData.class) {
                    return type.cast(new MaintenanceData()
                            .timestamp(parseSafeDateTime(getSafeString(jsonObject, "timestamp")))
                            .machineId(getSafeInt(jsonObject, "machineId"))
                            .component(getSafeString(jsonObject, "component"))
                            .maintenanceType(getSafeString(jsonObject, "maintenanceType")));
                }
                if (type == MachineData.class) {
                    String statusString = getSafeString(jsonObject, "status");
                    return type.cast(new MachineData()
                            .machineId(getSafeInt(jsonObject, "machineId"))
                            .model(getSafeString(jsonObject, "model"))
                            .age(getSafeInt(jsonObject, "age"))
                            .status(parseSafeStatus(statusString)));
                }



            }
        } catch (Exception e) {
            log.error("❌ Erro ao mapear JSON para '{}': {}", type.getSimpleName(), e.getMessage(), e);
        }
        return null;
    }

    private MachineData.StatusEnum parseSafeStatus(String status) {
        try {
            return status != null ? MachineData.StatusEnum.fromValue(status) : null;
        } catch (IllegalArgumentException e) {
            log.error("❌ Erro ao converter status '{}': {}", status, e.getMessage());
            return null;
        }
    }


    /**
     * Converte uma string de data e hora em OffsetDateTime de forma segura.
     */
    private OffsetDateTime parseSafeDateTime(String dateTime) {
        try {
            return (dateTime != null && !dateTime.isEmpty()) ? OffsetDateTime.parse(dateTime) : null;
        } catch (DateTimeParseException e) {
            log.error("❌ Erro ao converter timestamp '{}': {}", dateTime, e.getMessage(), e);
            return null;
        }
    }

    private String buildTimeRange(OffsetDateTime start, OffsetDateTime end) {
        if (start != null && end != null) {
            return "|> range(start: " + start.toInstant() + ", stop: " + end.toInstant() + ")";
        }
        return "|> range(start: -10m)";
    }

    private Integer getSafeInt(JsonElement json, String key) {
        if (json != null && json.isJsonObject()) { // Verifica se o elemento JSON é um objeto
            JsonObject jsonObject = json.getAsJsonObject();
            if (jsonObject.has(key)) {
                JsonElement value = jsonObject.get(key);
                if (value.isJsonPrimitive() && value.getAsJsonPrimitive().isNumber()) {
                    return value.getAsInt();
                }
            }
        }
        return null; // Retorna null se a chave não existir ou não for um número
    }


    private BigDecimal getSafeBigDecimal(JsonObject json, String key) {
        return json.has(key) && json.get(key).isJsonPrimitive() ? json.get(key).getAsBigDecimal() : null;
    }

    private String getSafeString(JsonObject json, String key) {
        return json.has(key) && json.get(key).isJsonPrimitive() ? json.get(key).getAsString() : null;
    }
}
