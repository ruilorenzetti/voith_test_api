package com.voith.consumer.queueconsumer;

import com.voith.consumer.model.ErrorData;
import com.voith.consumer.service.ConsolePrinterService;
import com.voith.consumer.service.InfluxDBService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;

@Slf4j
@Service
@RequiredArgsConstructor
public class ErrorConsumer {

    private final ConsolePrinterService consolePrinterService;
    private final InfluxDBService influxDBService;
    private final boolean isPrintMode;

    @KafkaListener(topics = "error_topic", groupId = "voith-consumer-group")
    public void consume(String message) {
        if (isPrintMode) {
            consolePrinterService.printMessage("ErrorConsumer", message);
        } else {
            log.info("📤 [ErrorConsumer] Storing message in InfluxDB: {}", message);
            influxDBService.storeData("error", message);
        }
    }
}
