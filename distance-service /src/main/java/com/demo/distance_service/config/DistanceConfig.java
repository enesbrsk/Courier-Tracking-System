package com.demo.distance_service.config;

import org.springframework.boot.context.properties.ConfigurationProperties;

@ConfigurationProperties(prefix = "app.distance")
public record DistanceConfig(
        String strategy,
        double minMovementThresholdMeters
) {
}
