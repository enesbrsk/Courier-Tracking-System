package com.demo.distance_service.config;

import com.demo.distance_service.exception.CourierNotFoundException;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.kafka.listener.CommonErrorHandler;
import org.springframework.kafka.listener.DefaultErrorHandler;
import org.springframework.util.backoff.FixedBackOff;

@Configuration
public class KafkaListenerConfig {

    @Bean
    CommonErrorHandler kafkaErrorHandler() {
        var errorHandler = new DefaultErrorHandler(new FixedBackOff(200L, 2L));
        errorHandler.addNotRetryableExceptions(CourierNotFoundException.class);
        return errorHandler;
    }
}
