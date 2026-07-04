package com.demo.courier_gateway_service.producer;


import com.demo.courier_gateway_service.model.dto.CourierLocationEventDTO;

public interface CourierLocationPublisher {
    void publish(CourierLocationEventDTO event);
}
