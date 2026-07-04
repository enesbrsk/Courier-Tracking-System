package com.demo.courier_gateway_service.service;

import com.demo.courier_gateway_service.model.dto.CourierLocationEventDTO;
import com.demo.courier_gateway_service.producer.CourierLocationPublisher;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.Instant;

import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class CourierLocationServiceTest {

    @Mock
    private CourierLocationPublisher locationPublisher;

    @InjectMocks
    private CourierLocationService courierLocationService;

    @Test
    @DisplayName("Should successfully process and publish valid courier location event")
    void shouldProcessAndPublishLocationSuccessfully() {
        // Given
        var event = new CourierLocationEventDTO(
                "courier-123",
                "courier1",
                40.9923307,
                29.1244229,
                Instant.now().getEpochSecond()
        );

        // When
        courierLocationService.processLocation(event);

        // Then
        verify(locationPublisher, times(1)).publish(event);
        verifyNoMoreInteractions(locationPublisher);
    }
}