package com.demo.store_service.service;

import com.demo.store_service.model.Store;
import com.demo.store_service.model.StoreProperties;
import com.demo.store_service.model.dto.CourierLocationEventDTO;
import com.demo.store_service.registry.StoreRegistry;
import com.demo.store_service.repository.CourierEntity;
import com.demo.store_service.repository.CourierRepository;
import com.demo.store_service.repository.CourierStoreEntryEntity;
import com.demo.store_service.repository.CourierStoreEntryRepository;
import com.demo.store_service.service.calculator.DistanceCalculator;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.Instant;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class StoreEntryEventHandlerTest {

    private static final Instant EVENT_TIME = Instant.parse("2024-07-03T13:46:40Z");
    private static final Instant EVENT_TIME_LATER = Instant.parse("2024-07-03T13:48:20Z");
    private static final Store ATASEHIR = new Store("Ataşehir MMM Migros", 40.9923307, 29.1244229);
    private static final CourierEntity COURIER = new CourierEntity("courier-1");

    @Mock
    CourierRepository courierRepository;

    @Mock
    StoreRegistry storeRegistry;

    @Mock
    DistanceCalculator distanceCalculator;

    @Mock
    StoreProperties storeProperties;

    @Mock
    ReEntryLockService reEntryLockService;

    @Mock
    CourierStoreEntryRepository courierStoreEntryRepository;

    @InjectMocks
    StoreEntryEventHandler storeEntryEventHandler;

    @BeforeEach
    void setUp() {
        when(courierRepository.findById("courier-1")).thenReturn(Optional.of(COURIER));
    }

    @Test
    void logsEntryWhenCourierIsWithinRadius() {
        when(storeRegistry.getStores()).thenReturn(List.of(ATASEHIR));
        when(storeProperties.entryRadiusMeters()).thenReturn(100.0);
        when(distanceCalculator.calculateMeters(40.9923307, 29.1244229, 40.9923307, 29.1244229)).thenReturn(0.0);
        when(reEntryLockService.isAllowed("courier-1", ATASEHIR.name())).thenReturn(true);

        storeEntryEventHandler.processStoreEntry(
                new CourierLocationEventDTO("evt-1", "courier-1", 40.9923307, 29.1244229, EVENT_TIME.toString()));

        var captor = ArgumentCaptor.forClass(CourierStoreEntryEntity.class);
        verify(courierStoreEntryRepository).save(captor.capture());
        assertEquals("courier-1", captor.getValue().getCourierId());
        assertEquals(ATASEHIR.name(), captor.getValue().getStoreName());
    }

    @Test
    void skipsWhenCourierIsOutsideRadius() {
        when(storeRegistry.getStores()).thenReturn(List.of(ATASEHIR));
        when(storeProperties.entryRadiusMeters()).thenReturn(100.0);
        when(distanceCalculator.calculateMeters(41.5, 29.0, 40.9923307, 29.1244229)).thenReturn(60000.0);

        storeEntryEventHandler.processStoreEntry(
                new CourierLocationEventDTO("evt-1", "courier-1", 41.5, 29.0, EVENT_TIME.toString()));

        verify(courierStoreEntryRepository, never()).save(any());
    }

    @Test
    void skipsReEntryWithinLockWindow() {
        when(storeRegistry.getStores()).thenReturn(List.of(ATASEHIR));
        when(storeProperties.entryRadiusMeters()).thenReturn(100.0);
        when(distanceCalculator.calculateMeters(40.9923307, 29.1244229, 40.9923307, 29.1244229)).thenReturn(0.0);
        when(reEntryLockService.isAllowed("courier-1", ATASEHIR.name())).thenReturn(false);

        storeEntryEventHandler.processStoreEntry(
                new CourierLocationEventDTO("evt-2", "courier-1", 40.9923307, 29.1244229, EVENT_TIME_LATER.toString()));

        verify(courierStoreEntryRepository, never()).save(any());
    }
}
