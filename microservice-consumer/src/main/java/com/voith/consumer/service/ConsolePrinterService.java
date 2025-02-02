package com.voith.consumer.service;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicInteger;

@Slf4j
@Service
public class ConsolePrinterService {

    private final ConcurrentHashMap<String, AtomicInteger> messageCounters = new ConcurrentHashMap<>();

    /**
     * Prints the received Kafka message, identifying the consumer and counting messages.
     *
     * @param consumerName Name of the consumer (e.g., "TelemetryConsumer")
     * @param message      The message received from Kafka
     */
    public void printMessage(String consumerName, String message) {
        messageCounters.putIfAbsent(consumerName, new AtomicInteger(0));
        int count = messageCounters.get(consumerName).incrementAndGet();
        log.info("🟢 [{}] Received message #{}: {}", consumerName, count, message);
    }
}
