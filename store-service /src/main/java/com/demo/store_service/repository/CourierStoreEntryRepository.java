package com.demo.store_service.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface CourierStoreEntryRepository extends JpaRepository<CourierStoreEntryEntity, Long> {

    List<CourierStoreEntryEntity> findByCourier_IdOrderByTimeDesc(String courierId);
}
