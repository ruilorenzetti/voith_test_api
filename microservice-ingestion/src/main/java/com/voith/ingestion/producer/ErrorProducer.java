package com.voith.ingestion.producer;

import com.voith.ingestion.model.ErrorData;
import com.google.gson.Gson;
import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.util.Random;
import java.util.concurrent.*;

@Slf4j
@Service
public class ErrorProducer {
    private final KafkaTemplate<String, String> kafkaTemplate;
    private ScheduledExecutorService scheduler;
    private ScheduledFuture<?> scheduledTask;
    private final Random random = new Random();
    private final Gson gson = new Gson();

    public ErrorProducer(KafkaTemplate<String, String> kafkaTemplate) {
        this.kafkaTemplate = kafkaTemplate;
    }

    public void startProducing(int intervalInSeconds) {
        scheduler = Executors.newScheduledThreadPool(1);
        scheduledTask = scheduler.scheduleAtFixedRate(() -> {
            ErrorData data = generateErrorData();
            kafkaTemplate.send("error_topic", gson.toJson(data));
            log.info("Sent Error: {}", data);
        }, 0, intervalInSeconds, TimeUnit.SECONDS);
    }

    public void stopProducing() {
        if (scheduledTask != null) {
            scheduledTask.cancel(true);
            scheduler.shutdown();
            log.info("Error producer stopped.");
        }
    }

    private ErrorData generateErrorData() {
        String[] errorTypes = {"Sensor Failure", "Overheating", "Network Issue", "Power Fluctuation"};

        ErrorData data = new ErrorData();
        data.setTimestamp(Instant.now().toString());
        data.setMachineId(random.nextInt(100));
        data.setErrorType(errorTypes[random.nextInt(errorTypes.length)]);
        return data;
    }
}
