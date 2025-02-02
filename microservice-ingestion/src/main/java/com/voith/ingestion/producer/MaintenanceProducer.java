package com.voith.ingestion.producer;

import com.voith.ingestion.model.MaintenanceData;
import com.google.gson.Gson;
import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.util.Random;
import java.util.concurrent.*;

@Slf4j
@Service
public class MaintenanceProducer {
    private final KafkaTemplate<String, String> kafkaTemplate;
    private ScheduledExecutorService scheduler;
    private ScheduledFuture<?> scheduledTask;
    private final Random random = new Random();
    private final Gson gson = new Gson();

    public MaintenanceProducer(KafkaTemplate<String, String> kafkaTemplate) {
        this.kafkaTemplate = kafkaTemplate;
    }

    public void startProducing(int intervalInSeconds) {
        scheduler = Executors.newScheduledThreadPool(1);
        scheduledTask = scheduler.scheduleAtFixedRate(() -> {
            MaintenanceData data = generateMaintenanceData();
            kafkaTemplate.send("maintenance_topic", gson.toJson(data));
            log.info("Sent Maintenance: {}", data);
        }, 0, intervalInSeconds, TimeUnit.SECONDS);
    }

    public void stopProducing() {
        if (scheduledTask != null) {
            scheduledTask.cancel(true);
            scheduler.shutdown();
            log.info("Maintenance producer stopped.");
        }
    }

    private MaintenanceData generateMaintenanceData() {
        String[] components = {"Hydraulic Pump", "Cooling Fan", "Power Supply", "Compressor"};
        String[] maintenanceTypes = {"Scheduled", "Emergency Repair"};

        MaintenanceData data = new MaintenanceData();
        data.setTimestamp(Instant.now().toString());
        data.setMachineId(random.nextInt(100));
        data.setComponent(components[random.nextInt(components.length)]);
        data.setMaintenanceType(maintenanceTypes[random.nextInt(maintenanceTypes.length)]);
        return data;
    }
}
