package com.demo.store_service.model;

import org.springframework.boot.context.properties.ConfigurationProperties;

@ConfigurationProperties(prefix = "app.kafka")
public record KafkaTopicProperties(
        String courierLocationTopic
) {}
