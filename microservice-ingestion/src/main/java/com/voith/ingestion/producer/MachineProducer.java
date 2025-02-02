package com.voith.ingestion.producer;

import com.voith.ingestion.model.MachineData;
import com.google.gson.Gson;
import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

import java.util.Random;
import java.util.concurrent.*;

@Slf4j
@Service
public class MachineProducer {
    private final KafkaTemplate<String, String> kafkaTemplate;
    private ScheduledExecutorService scheduler;
    private ScheduledFuture<?> scheduledTask;
    private final Random random = new Random();
    private final Gson gson = new Gson();

    public MachineProducer(KafkaTemplate<String, String> kafkaTemplate) {
        this.kafkaTemplate = kafkaTemplate;
    }

    public void startProducing(int intervalInSeconds) {
        scheduler = Executors.newScheduledThreadPool(1);
        scheduledTask = scheduler.scheduleAtFixedRate(() -> {
            MachineData data = generateMachineData();
            kafkaTemplate.send("machine_topic", gson.toJson(data));
            log.info("Sent Machine Data: {}", data);
        }, 0, intervalInSeconds, TimeUnit.SECONDS);
    }

    public void stopProducing() {
        if (scheduledTask != null) {
            scheduledTask.cancel(true);
            scheduler.shutdown();
            log.info("Machine producer stopped.");
        }
    }

    private MachineData generateMachineData() {
        String[] models = {"Model A", "Model B", "Model C", "Model D"};
        MachineData.StatusEnum[] statuses = MachineData.StatusEnum.values(); // Obtém todos os valores do enum

        MachineData data = new MachineData();
        data.setMachineId(random.nextInt(100));
        data.setModel(models[random.nextInt(models.length)]);
        data.setAge(random.nextInt(10) + 1); // Idade entre 1 e 10 anos
        data.setStatus(statuses[random.nextInt(statuses.length)]); // Define um status aleatório

        return data;
    }

}
