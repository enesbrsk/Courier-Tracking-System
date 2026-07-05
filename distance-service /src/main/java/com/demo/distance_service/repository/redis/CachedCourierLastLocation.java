package com.demo.distance_service.repository.redis;

import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.redis.core.RedisHash;

@Getter
@NoArgsConstructor
@RedisHash(value = "last_location")
public class CachedCourierLastLocation {

    @Id
    private String courierId;
    private double latitude;
    private double longitude;
    private long timestamp;

    public CachedCourierLastLocation(String courierId, double latitude, double longitude, long timestamp) {
        this.courierId = courierId;
        this.latitude = latitude;
        this.longitude = longitude;
        this.timestamp = timestamp;
    }
}
