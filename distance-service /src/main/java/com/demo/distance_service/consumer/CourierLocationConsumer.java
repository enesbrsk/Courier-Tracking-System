package com.demo.distance_service.consumer;

import com.demo.distance_service.model.dto.CourierLocationEventDTO;
import com.demo.distance_service.service.CourierLocationEventHandler;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;

@Slf4j
@Component
@RequiredArgsConstructor
public class CourierLocationConsumer {

    private final CourierLocationEventHandler courierLocationEventHandler;

    @KafkaListener(
            topics = "${app.kafka.courier-location-topic}",
            groupId = "${spring.kafka.consumer.group-id}"
    )
    public void consume(CourierLocationEventDTO event) {
        log.debug("Consumed event. courier={}, eventId={}", event.courier(), event.eventId());

        try {
            courierLocationEventHandler.processCourierLocation(event);
        } catch (Exception ex) {
            log.error("Failed to process location event. courier={}, eventId={}",
                    event.courier(), event.eventId(), ex);
            throw ex;
        }
    }
}
