package com.voith.consumer.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class ConsumerModeConfig {

    @Value("${consumer.mode}")
    private String consumerMode;

    @Bean
    public boolean isPrintMode() {
        return "print".equalsIgnoreCase(consumerMode);
    }
}
