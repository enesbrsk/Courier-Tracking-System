package com.demo.store_service.service;

import com.demo.store_service.exception.CourierNotFoundException;
import com.demo.store_service.model.Store;
import com.demo.store_service.model.StoreProperties;
import com.demo.store_service.model.dto.CourierLocationEventDTO;
import com.demo.store_service.registry.StoreRegistry;
import com.demo.store_service.repository.CourierEntity;
import com.demo.store_service.repository.CourierRepository;
import com.demo.store_service.repository.CourierStoreEntryEntity;
import com.demo.store_service.repository.CourierStoreEntryRepository;
import com.demo.store_service.service.calculator.DistanceCalculator;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Slf4j
@Service
@RequiredArgsConstructor
public class StoreEntryEventHandler {

    private final CourierRepository courierRepository;
    private final StoreRegistry storeRegistry;
    private final DistanceCalculator distanceCalculator;
    private final StoreProperties storeProperties;
    private final ReEntryLockService reEntryLockService;
    private final CourierStoreEntryRepository courierStoreEntryRepository;

    @Transactional
    public void processStoreEntry(CourierLocationEventDTO event) {
        var courier = courierRepository.findById(event.courier())
                .orElseThrow(() -> new CourierNotFoundException(event.courier()));

        for (Store store : storeRegistry.getStores()) {
            double distance = distanceCalculator.calculateMeters(
                    event.latitude(), event.longitude(),
                    store.lat(), store.lng());

            if (distance > storeProperties.entryRadiusMeters()) {
                continue;
            }

            if (!reEntryLockService.isAllowed(event.courier(), store.name())) {
                log.info("Re-entry skipped. courier={}, store={}", event.courier(), store.name());
                continue;
            }

            courierStoreEntryRepository.save(new CourierStoreEntryEntity(
                    courier, store.name(), event.timestamp()));

            log.info("Store entry logged. courier={}, store={}", event.courier(), store.name());
            break;
        }
    }
}
