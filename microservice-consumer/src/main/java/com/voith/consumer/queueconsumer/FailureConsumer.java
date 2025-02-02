package com.voith.consumer.queueconsumer;

import com.voith.consumer.service.ConsolePrinterService;
import com.voith.consumer.service.InfluxDBService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;

@Slf4j
@Service
@RequiredArgsConstructor
public class FailureConsumer {

    private final ConsolePrinterService consolePrinterService;
    private final InfluxDBService influxDBService;
    private final boolean isPrintMode;

    @KafkaListener(topics = "failure_topic", groupId = "voith-consumer-group")
    public void consume(String message) {
        if (isPrintMode) {
            consolePrinterService.printMessage("FailureConsumer", message);
        } else {
            log.info("📤 [FailureConsumer] Storing message in InfluxDB: {}", message);
            influxDBService.storeData("failure", message);
        }
    }
}
