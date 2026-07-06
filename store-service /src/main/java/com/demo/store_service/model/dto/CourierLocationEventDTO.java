package com.demo.store_service.model.dto;

public record CourierLocationEventDTO(
        String eventId,
        String courier,
        Double latitude,
        Double longitude,
        Long timestamp
) {}
