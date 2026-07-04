package com.demo.courier_gateway_service.model;

import org.springframework.boot.context.properties.ConfigurationProperties;

@ConfigurationProperties(prefix = "app.kafka")
public record KafkaTopicProperties(
        String courierLocationTopic
) {}