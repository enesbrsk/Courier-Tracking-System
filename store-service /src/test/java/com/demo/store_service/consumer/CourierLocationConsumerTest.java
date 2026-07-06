package com.demo.store_service.consumer;

import com.demo.store_service.model.dto.CourierLocationEventDTO;
import com.demo.store_service.service.StoreEntryEventHandler;
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
    StoreEntryEventHandler storeEntryEventHandler;

    @InjectMocks
    CourierLocationConsumer courierLocationConsumer;

    @Test
    void delegatesToHandler() {
        var event = new CourierLocationEventDTO("courier-1_1720000000", "courier-1", 40.99, 29.12, 1720000000L);

        courierLocationConsumer.consume(event);

        verify(storeEntryEventHandler).processStoreEntry(event);
    }

    @Test
    void rethrowsHandlerException() {
        var event = new CourierLocationEventDTO("courier-1_1720000000", "courier-1", 40.99, 29.12, 1720000000L);
        doThrow(new RuntimeException("db down")).when(storeEntryEventHandler).processStoreEntry(event);

        assertThrows(RuntimeException.class, () -> courierLocationConsumer.consume(event));
    }
}
