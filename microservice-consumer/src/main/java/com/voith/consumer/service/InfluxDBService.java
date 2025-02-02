package com.voith.consumer.service;

import com.influxdb.client.InfluxDBClient;
import com.influxdb.client.QueryApi;
import com.influxdb.client.WriteApi;
import com.influxdb.client.domain.WritePrecision;
import com.influxdb.client.write.Point;
import com.influxdb.query.FluxTable;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Slf4j
@Service
public class InfluxDBService {

    private final InfluxDBClient influxDBClient;
    private final QueryApi queryApi;
    private final String bucket;
    private final String organization;

    /**
     * Constructor for InfluxDBService.
     * Ensures QueryApi is initialized and properties are injected correctly.
     */
    public InfluxDBService(InfluxDBClient influxDBClient,
                           @Value("${influxdb.bucket}") String bucket,
                           @Value("${influxdb.org}") String organization) {
        this.influxDBClient = influxDBClient;
        this.queryApi = influxDBClient.getQueryApi(); // ✅ Obtendo QueryApi corretamente
        this.bucket = bucket;
        this.organization = organization;
    }


    /**
     * Executes a query in InfluxDB and returns results.
     *
     * @return List of formatted results.
     */
    public List<String> executeQuery() {
        String query = String.format("from(bucket: \"%s\") |> range(start: -10m) |> filter(fn: (r) => r._measurement == \"telemetry\")", bucket);

        List<FluxTable> tables = queryApi.query(query);
        List<String> results = new ArrayList<>();

        for (FluxTable table : tables) {
            table.getRecords().forEach(record -> {
                String result = String.format("⏳ Timestamp: %s, 📌 Campo: %s, 📊 Valor: %s",
                        record.getTime(), record.getField(), record.getValue());
                results.add(result);
            });
        }

        return results;
    }

    /**
     * Stores data in InfluxDB.
     *
     * @param measurement The measurement name (e.g., "telemetry", "errors").
     * @param jsonData    The JSON-formatted data.
     */
    public void storeData(String measurement, String jsonData) {
        try (WriteApi writeApi = influxDBClient.getWriteApi()) {
            Point point = Point
                    .measurement(measurement)
                    .addField("data", jsonData)
                    .time(Instant.now(), WritePrecision.NS);

            writeApi.writePoint(bucket, organization, point);
            log.info("✅ Data stored in InfluxDB [{}]: {}", measurement, jsonData);
        } catch (Exception e) {
            log.error("❌ Error storing data in InfluxDB: {}", e.getMessage());
        }
    }
}
