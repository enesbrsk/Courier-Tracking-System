package com.demo.store_service.consumer;

import com.demo.store_service.model.dto.CourierLocationEventDTO;
import com.demo.store_service.service.StoreEntryEventHandler;
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
    StoreEntryEventHandler storeEntryEventHandler;

    @InjectMocks
    CourierLocationConsumer courierLocationConsumer;

    @Test
    void consume_ValidEvent_ShouldDelegateToHandler() {
        var event = new CourierLocationEventDTO("courier-1_1720000000", "courier-1", 40.99, 29.12, EVENT_TIME.toString());

        courierLocationConsumer.consume(event);

        verify(storeEntryEventHandler).processStoreEntry(event);
    }

    @Test
    void consume_HandlerFailure_ShouldRethrowException() {
        var event = new CourierLocationEventDTO("courier-1_1720000000", "courier-1", 40.99, 29.12, EVENT_TIME.toString());
        doThrow(new RuntimeException("db down")).when(storeEntryEventHandler).processStoreEntry(event);

        assertThrows(RuntimeException.class, () -> courierLocationConsumer.consume(event));
    }
}
