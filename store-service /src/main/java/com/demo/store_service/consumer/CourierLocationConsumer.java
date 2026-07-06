package com.demo.store_service.consumer;

import com.demo.store_service.model.dto.CourierLocationEventDTO;
import com.demo.store_service.service.StoreEntryEventHandler;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;

@Slf4j
@Component
@RequiredArgsConstructor
public class CourierLocationConsumer {

    private final StoreEntryEventHandler storeEntryEventHandler;

    @KafkaListener(
            topics = "${app.kafka.courier-location-topic}",
            groupId = "${spring.kafka.consumer.group-id}"
    )
    public void consume(CourierLocationEventDTO event) {
        log.debug("Consumed event. courierId={}, eventId={}", event.courierId(), event.eventId());

        try {
            storeEntryEventHandler.processStoreEntry(event);
        } catch (Exception ex) {
            log.error("Failed to process location event. courierId={}, eventId={}",
                    event.courierId(), event.eventId(), ex);
            throw ex;
        }
    }
}
