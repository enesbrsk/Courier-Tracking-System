package com.demo.store_service.model;

import org.springframework.boot.context.properties.ConfigurationProperties;

@ConfigurationProperties(prefix = "app.store")
public record StoreProperties(
        double entryRadiusMeters,
        long reentryLockSeconds
) {
}
