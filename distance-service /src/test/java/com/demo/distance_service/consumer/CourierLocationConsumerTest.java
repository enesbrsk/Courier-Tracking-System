package com.demo.distance_service.consumer;

import com.demo.distance_service.model.dto.CourierLocationEventDTO;
import com.demo.distance_service.service.CourierLocationEventHandler;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.verify;

@ExtendWith(MockitoExtension.class)
class CourierLocationConsumerTest {

    @Mock
    CourierLocationEventHandler courierLocationEventHandler;

    @InjectMocks
    CourierLocationConsumer courierLocationConsumer;

    @Test
    void delegatesToHandler() {
        var event = new CourierLocationEventDTO("courier-1_1720000000", "courier-1", 40.99, 29.12, 1720000000L);

        courierLocationConsumer.consume(event);

        verify(courierLocationEventHandler).processCourierLocation(event);
    }

    @Test
    void rethrowsHandlerException() {
        var event = new CourierLocationEventDTO("courier-1_1720000000", "courier-1", 40.99, 29.12, 1720000000L);
        doThrow(new RuntimeException("db down")).when(courierLocationEventHandler).processCourierLocation(event);

        assertThrows(RuntimeException.class, () -> courierLocationConsumer.consume(event));
    }
}
