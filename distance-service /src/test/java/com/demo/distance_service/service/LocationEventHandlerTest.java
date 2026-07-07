package com.demo.distance_service.service;

import com.demo.distance_service.model.DistanceProperties;
import com.demo.distance_service.model.dto.CourierLocationEventDTO;
import com.demo.distance_service.repository.CourierDistanceEntity;
import com.demo.distance_service.repository.CourierDistanceRepository;
import com.demo.distance_service.repository.CourierEntity;
import com.demo.distance_service.repository.CourierRepository;
import com.demo.distance_service.service.calculator.DistanceCalculator;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.Instant;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.anyDouble;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class LocationEventHandlerTest {

    private static final Instant EVENT_TIME = Instant.parse("2024-07-03T13:46:40Z");
    private static final Instant EVENT_TIME_LATER = Instant.parse("2024-07-03T13:48:20Z");
    private static final CourierEntity COURIER = new CourierEntity("courier-1");

    @Mock
    CourierRepository courierRepository;

    @Mock
    CourierDistanceRepository courierDistanceRepository;

    @Mock
    DistanceCalculator distanceCalculator;

    @Mock
    DistanceProperties distanceProperties;

    @InjectMocks
    CourierLocationEventHandler courierLocationEventHandler;

    @Test
    void processCourierLocation_FirstLocation_ShouldSaveWithoutAddingDistance() {
        when(courierRepository.findById("courier-1")).thenReturn(Optional.of(COURIER));
        when(courierDistanceRepository.findByCourier_Id("courier-1"))
                .thenReturn(Optional.of(new CourierDistanceEntity(COURIER)));

        courierLocationEventHandler.processCourierLocation(
                new CourierLocationEventDTO("evt-1", "courier-1", 40.99, 29.12, EVENT_TIME.toString()));

        var captor = ArgumentCaptor.forClass(CourierDistanceEntity.class);
        verify(courierDistanceRepository).save(captor.capture());
        assertEquals(0.0, captor.getValue().getTotalDistance());
        assertEquals("courier-1", captor.getValue().getCourierId());
        verify(distanceCalculator, never()).calculateMeters(anyDouble(), anyDouble(), anyDouble(), anyDouble());
    }

    @Test
    void processCourierLocation_MissingDistanceRecord_ShouldCreateAndSave() {
        when(courierRepository.findById("courier-1")).thenReturn(Optional.of(COURIER));
        when(courierDistanceRepository.findByCourier_Id("courier-1")).thenReturn(Optional.empty());

        courierLocationEventHandler.processCourierLocation(
                new CourierLocationEventDTO("evt-1", "courier-1", 40.99, 29.12, EVENT_TIME.toString()));

        var captor = ArgumentCaptor.forClass(CourierDistanceEntity.class);
        verify(courierDistanceRepository).save(captor.capture());
        assertEquals("courier-1", captor.getValue().getCourierId());
        assertEquals(0.0, captor.getValue().getTotalDistance());
    }

    @Test
    void processCourierLocation_MovementAboveThreshold_ShouldAddDistance() {
        when(courierRepository.findById("courier-1")).thenReturn(Optional.of(COURIER));
        var existing = new CourierDistanceEntity(COURIER);
        existing.updateLastLocation(40.99, 29.12, EVENT_TIME);
        when(courierDistanceRepository.findByCourier_Id("courier-1")).thenReturn(Optional.of(existing));
        when(distanceCalculator.calculateMeters(40.99, 29.12, 41.00, 29.13)).thenReturn(1500.0);
        when(distanceProperties.minMovementThresholdMeters()).thenReturn(10.0);

        courierLocationEventHandler.processCourierLocation(
                new CourierLocationEventDTO("evt-2", "courier-1", 41.00, 29.13, EVENT_TIME_LATER.toString()));

        var captor = ArgumentCaptor.forClass(CourierDistanceEntity.class);
        verify(courierDistanceRepository).save(captor.capture());
        assertEquals(1500.0, captor.getValue().getTotalDistance());
    }

    @Test
    void processCourierLocation_MovementBelowThreshold_ShouldNotAddDistance() {
        when(courierRepository.findById("courier-1")).thenReturn(Optional.of(COURIER));
        var existing = new CourierDistanceEntity(COURIER);
        existing.updateLastLocation(40.99, 29.12, EVENT_TIME);
        existing.addDistance(100.0);
        when(courierDistanceRepository.findByCourier_Id("courier-1")).thenReturn(Optional.of(existing));
        when(distanceCalculator.calculateMeters(40.99, 29.12, 40.9901, 29.1201)).thenReturn(5.0);
        when(distanceProperties.minMovementThresholdMeters()).thenReturn(10.0);

        courierLocationEventHandler.processCourierLocation(
                new CourierLocationEventDTO("evt-2", "courier-1", 40.9901, 29.1201, EVENT_TIME_LATER.toString()));

        var captor = ArgumentCaptor.forClass(CourierDistanceEntity.class);
        verify(courierDistanceRepository).save(captor.capture());
        assertEquals(100.0, captor.getValue().getTotalDistance());
    }
}
