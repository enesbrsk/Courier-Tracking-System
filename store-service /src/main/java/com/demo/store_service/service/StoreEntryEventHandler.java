package com.demo.store_service.service;

import com.demo.store_service.exception.CourierNotFoundException;
import com.demo.store_service.model.Store;
import com.demo.store_service.model.StoreProperties;
import com.demo.store_service.model.dto.CourierLocationEventDTO;
import com.demo.store_service.registry.StoreRegistry;
import com.demo.store_service.repository.CourierRepository;
import com.demo.store_service.repository.CourierStoreEntryEntity;
import com.demo.store_service.repository.CourierStoreEntryRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Slf4j
@Service
@RequiredArgsConstructor
public class StoreEntryEventHandler {

    private final CourierRepository courierRepository;
    private final StoreRegistry storeRegistry;
    private final DistanceService distanceService;
    private final StoreProperties storeProperties;
    private final ReEntryLockService reEntryLockService;
    private final CourierStoreEntryRepository courierStoreEntryRepository;

    @Transactional
    public void processStoreEntry(CourierLocationEventDTO event) {
        var courier = courierRepository.findById(event.courierId())
                .orElseThrow(() -> new CourierNotFoundException(event.courierId()));

        for (Store store : storeRegistry.getStores()) {
            double distance = distanceService.calculateMeters(
                    event.latitude(), event.longitude(),
                    store.lat(), store.lng());

            if (distance > storeProperties.entryRadiusMeters()) {
                continue;
            }

            if (!reEntryLockService.isAllowed(event.courierId(), store.name())) {
                log.info("Re-entry skipped. courierId={}, store={}", event.courierId(), store.name());
                continue;
            }

            courierStoreEntryRepository.save(new CourierStoreEntryEntity(
                    courier, store.name(), event.eventTime()));

            log.info("Store entry logged. courierId={}, store={}", event.courierId(), store.name());
            break;
        }
    }
}
