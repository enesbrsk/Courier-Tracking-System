package com.demo.courier_gateway_service.service;

import com.demo.courier_gateway_service.model.dto.CourierLocationEventDTO;
import com.demo.courier_gateway_service.producer.CourierLocationPublisher;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.Instant;

import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

@ExtendWith(MockitoExtension.class)
class CourierLocationServiceTest {

    private static final Instant EVENT_TIME = Instant.parse("2024-07-03T13:46:40Z");

    @Mock
    private CourierLocationPublisher locationPublisher;

    @InjectMocks
    private CourierLocationService courierLocationService;

    @Test
    void shouldProcessAndPublishLocationSuccessfully() {
        var event = new CourierLocationEventDTO(
                "courier1_1720000000",
                "courier1",
                40.9923307,
                29.1244229,
                EVENT_TIME
        );

        courierLocationService.processLocation(event);

        verify(locationPublisher, times(1)).publish(event);
    }
}
