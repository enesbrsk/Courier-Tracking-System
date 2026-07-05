package com.demo.distance_service.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.Optional;

public interface CourierRepository extends JpaRepository<CourierEntity, String> {

    @Query("SELECT c FROM CourierEntity c LEFT JOIN FETCH c.distance WHERE c.id = :id")
    Optional<CourierEntity> findByIdWithDistance(@Param("id") String id);
}
