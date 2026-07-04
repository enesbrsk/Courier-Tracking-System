package com.demo.courier_gateway_service.producer;

import com.demo.courier_gateway_service.model.KafkaTopicProperties;
import com.demo.courier_gateway_service.model.dto.CourierLocationEventDTO;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Component;

@Slf4j
@Component
@RequiredArgsConstructor
public class KafkaCourierLocationAdapter implements CourierLocationPublisher {

    private final KafkaTemplate<String, Object> kafkaTemplate;
    private final KafkaTopicProperties topicProperties;

    @Override
    public void publish(CourierLocationEventDTO event) {
        var topic = topicProperties.courierLocationTopic();
        var partitionKey = event.courier();

        log.debug("Publishing event to Kafka. Topic: {}, PartitionKey: {}", topic, partitionKey);

        kafkaTemplate.send(topic, partitionKey, event)
                .whenComplete((result, exception) -> {
                    if (exception != null) {
                        log.error("Failed to publish location event. CourierID: {}, Error: {}",
                                partitionKey, exception.getMessage(), exception);
                    } else {
                        log.info("Successfully published location event. CourierID: {}, Partition: {}, Offset: {}",
                                partitionKey,
                                result.getRecordMetadata().partition(),
                                result.getRecordMetadata().offset());
                    }
                });
    }
}