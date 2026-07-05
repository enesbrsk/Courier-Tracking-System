package com.demo.distance_service.repository.redis;

import org.springframework.data.repository.CrudRepository;

public interface CachedLastLocationRepository extends CrudRepository<CachedCourierLastLocation, String> {
}
