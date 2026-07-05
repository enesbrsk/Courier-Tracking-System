package com.demo.distance_service.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface CourierDistanceRepository extends JpaRepository<CourierDistanceEntity, Long> {
    Optional<CourierDistanceEntity> findByCourierId(String courierId);
}
