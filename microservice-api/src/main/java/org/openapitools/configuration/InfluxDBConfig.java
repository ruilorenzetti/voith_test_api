package org.openapitools.configuration;

import com.influxdb.client.InfluxDBClient;
import com.influxdb.client.InfluxDBClientFactory;
import com.influxdb.client.QueryApi;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class InfluxDBConfig {

    private static final String INFLUXDB_URL = "http://localhost:8086";
    private static final String INFLUXDB_TOKEN = "YGtcbtqrAYJpVYcRUgMuLSyH-hYjxWPWP9WFS_N_mismbFgaILmtNt6uqwNjXD24TZMW3lOmehjmWvTE0S1CdQ==";
    private static final String INFLUXDB_ORG = "my-org";
    private static final String INFLUXDB_BUCKET = "my-bucket";

    @Bean
    public InfluxDBClient influxDBClient() {
        return InfluxDBClientFactory.create(INFLUXDB_URL, INFLUXDB_TOKEN.toCharArray(), INFLUXDB_ORG, INFLUXDB_BUCKET);
    }

    @Bean
    public QueryApi queryApi(InfluxDBClient influxDBClient) {
        return influxDBClient.getQueryApi();
    }
}
