package com.voith.ingestion.producer;

import com.voith.ingestion.model.FailureData;
import com.google.gson.Gson;
import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.util.Random;
import java.util.concurrent.*;

@Slf4j
@Service
public class FailureProducer {
    private final KafkaTemplate<String, String> kafkaTemplate;
    private ScheduledExecutorService scheduler;
    private ScheduledFuture<?> scheduledTask;
    private final Random random = new Random();
    private final Gson gson = new Gson();

    public FailureProducer(KafkaTemplate<String, String> kafkaTemplate) {
        this.kafkaTemplate = kafkaTemplate;
    }

    public void startProducing(int intervalInSeconds) {
        scheduler = Executors.newScheduledThreadPool(1);
        scheduledTask = scheduler.scheduleAtFixedRate(() -> {
            FailureData data = generateFailureData();
            kafkaTemplate.send("failure_topic", gson.toJson(data));
            log.info("Sent Failure: {}", data);
        }, 0, intervalInSeconds, TimeUnit.SECONDS);
    }

    public void stopProducing() {
        if (scheduledTask != null) {
            scheduledTask.cancel(true);
            scheduler.shutdown();
            log.info("Failure producer stopped.");
        }
    }

    private FailureData generateFailureData() {
        String[] failureTypes = {"Motor Breakdown", "Sensor Malfunction", "Hydraulic Leak", "Overload Failure"};

        FailureData data = new FailureData();
        data.setTimestamp(Instant.now().toString());
        data.setMachineId(random.nextInt(100));
        data.setFailureType(failureTypes[random.nextInt(failureTypes.length)]);
        return data;
    }
}
