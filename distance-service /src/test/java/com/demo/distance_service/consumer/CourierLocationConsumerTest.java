package com.demo.distance_service.consumer;

import com.demo.distance_service.model.dto.CourierLocationEventDTO;
import com.demo.distance_service.service.CourierLocationEventHandler;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.Instant;

import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.verify;

@ExtendWith(MockitoExtension.class)
class CourierLocationConsumerTest {

    private static final Instant EVENT_TIME = Instant.parse("2024-07-03T13:46:40Z");

    @Mock
    CourierLocationEventHandler courierLocationEventHandler;

    @InjectMocks
    CourierLocationConsumer courierLocationConsumer;

    @Test
    void consume_ValidEvent_ShouldDelegateToHandler() {
        var event = new CourierLocationEventDTO("courier-1_1720000000", "courier-1", 40.99, 29.12, EVENT_TIME.toString());

        courierLocationConsumer.consume(event);

        verify(courierLocationEventHandler).processCourierLocation(event);
    }

    @Test
    void consume_HandlerFailure_ShouldRethrowException() {
        var event = new CourierLocationEventDTO("courier-1_1720000000", "courier-1", 40.99, 29.12, EVENT_TIME.toString());
        doThrow(new RuntimeException("db down")).when(courierLocationEventHandler).processCourierLocation(event);

        assertThrows(RuntimeException.class, () -> courierLocationConsumer.consume(event));
    }
}
