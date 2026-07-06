package com.demo.store_service.service;

import com.demo.store_service.model.StoreProperties;
import lombok.RequiredArgsConstructor;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Service;

import java.time.Duration;

@Service
@RequiredArgsConstructor
public class ReEntryLockService {

    private static final String KEY_PREFIX = "reentry:";

    private final StringRedisTemplate redisTemplate;
    private final StoreProperties storeProperties;

    public boolean isAllowed(String courierId, String storeName) {
        String key = KEY_PREFIX + courierId + ":" + storeName;
        Duration ttl = Duration.ofSeconds(storeProperties.reentryLockSeconds());

        return Boolean.TRUE.equals(redisTemplate.opsForValue().setIfAbsent(key, "locked", ttl));
    }
}
