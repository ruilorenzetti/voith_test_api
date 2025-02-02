package com.voith.ingestion.producer;

import com.voith.ingestion.model.TelemetryData;
import com.google.gson.Gson;
import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.util.Random;
import java.util.concurrent.*;

@Slf4j
@Service
public class TelemetryProducer {
    private final KafkaTemplate<String, String> kafkaTemplate;
    private ScheduledExecutorService scheduler;
    private ScheduledFuture<?> scheduledTask;
    private final Random random = new Random();
    private final Gson gson = new Gson();

    public TelemetryProducer(KafkaTemplate<String, String> kafkaTemplate) {
        this.kafkaTemplate = kafkaTemplate;
    }

    public void startProducing(int intervalInSeconds) {
        scheduler = Executors.newScheduledThreadPool(1);
        scheduledTask = scheduler.scheduleAtFixedRate(() -> {
            TelemetryData data = generateTelemetryData();
            kafkaTemplate.send("telemetry_topic", gson.toJson(data));
            log.info("Sent: {}", data);
        }, 0, intervalInSeconds, TimeUnit.SECONDS);
    }

    public void stopProducing() {
        if (scheduledTask != null) {
            scheduledTask.cancel(true);
            scheduler.shutdown();
            log.info("Telemetry producer stopped.");
        }
    }

    private TelemetryData generateTelemetryData() {
        TelemetryData data = new TelemetryData();
        data.setTimestamp(Instant.now().toString());
        data.setMachineId(random.nextInt(100));
        data.setVoltage(220 + random.nextDouble() * 10);
        data.setRotation(1500 + random.nextDouble() * 100);
        data.setPressure(5 + random.nextDouble() * 2);
        data.setVibration(random.nextDouble());
        return data;
    }
}
