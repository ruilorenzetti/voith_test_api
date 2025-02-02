package com.voith.ingestion;

import com.voith.ingestion.service.ProducerService;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class MicroserviceIngestionApplication implements CommandLineRunner {

    private final ProducerService producerService;

    public MicroserviceIngestionApplication(ProducerService producerService) {
        this.producerService = producerService;
    }

    public static void main(String[] args) {
        SpringApplication.run(MicroserviceIngestionApplication.class, args);
    }

    @Override
    public void run(String... args) {
        producerService.startAllProducers(5); // Adjust the interval here
    }
}
