package com.demo.courier_gateway_service.service;

import com.demo.courier_gateway_service.model.dto.CourierLocationEventDTO;
import com.demo.courier_gateway_service.producer.CourierLocationPublisher;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class CourierLocationServiceTest {

    @Mock
    private CourierLocationPublisher locationPublisher;

    @InjectMocks
    private CourierLocationService courierLocationService;

    @Test
    void shouldProcessAndPublishLocationSuccessfully() {
        // Given
        var event = new CourierLocationEventDTO(
                "courier1_1720000000",
                "courier1",
                40.9923307,
                29.1244229,
                1720000000L
        );

        // When
        courierLocationService.processLocation(event);

        // Then
        verify(locationPublisher, times(1)).publish(event);
    }
}