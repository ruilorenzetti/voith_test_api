package com.voith.influxdb;

import com.google.gson.JsonObject;
import okhttp3.*;

import java.io.IOException;

public class InfluxDBQueryApp {
    private static final String INFLUXDB_URL = "http://localhost:8086";
    private static final String INFLUXDB_TOKEN = "YGtcbtqrAYJpVYcRUgMuLSyH-hYjxWPWP9WFS_N_mismbFgaILmtNt6uqwNjXD24TZMW3lOmehjmWvTE0S1CdQ==";
    private static final String INFLUXDB_ORG_ID = "b24eb3d664c1dabd";  // O orgID correto
    private static final String INFLUXDB_BUCKET = "my-bucket";

    private static final OkHttpClient client = new OkHttpClient();

    public static void main(String[] args) {
        try {
            // Executar a consulta
            queryTimeSeries();

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * Faz uma consulta no InfluxDB e imprime os resultados.
     */
    private static void queryTimeSeries() throws IOException {
        System.out.println("🔍 Consultando série temporal...");

        String query = "from(bucket:\"" + INFLUXDB_BUCKET + "\") " +
                "|> range(start: -10m) " +
                "|> filter(fn: (r) => r._measurement == \"telemetry\")";

        String url = INFLUXDB_URL + "/api/v2/query?org=" + INFLUXDB_ORG_ID;  // ✅ Adicionando `org` na URL

        JsonObject jsonBody = new JsonObject();
        jsonBody.addProperty("query", query);
        jsonBody.addProperty("type", "flux");

        RequestBody body = RequestBody.create(jsonBody.toString(), MediaType.get("application/json"));
        Request request = new Request.Builder()
                .url(url)
                .header("Authorization", "Token " + INFLUXDB_TOKEN)
                .header("Content-Type", "application/json")
                .post(body)
                .build();

        try (Response response = client.newCall(request).execute()) {
            if (!response.isSuccessful()) {
                System.err.println("❌ Query falhou: " + response.body().string());
                return;
            }
            String responseBody = response.body().string();
            System.out.println("✅ Resultado da Query: " + responseBody);
        }
    }
}
