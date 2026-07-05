package com.demo.distance_service.model;

import org.springframework.boot.context.properties.ConfigurationProperties;

@ConfigurationProperties(prefix = "app.distance")
public record DistanceProperties(
        double minMovementThresholdMeters
) {}
