package com.demo.store_service.model.dto;

import java.time.Instant;

public record CourierLocationEventDTO(
        String eventId,
        String courierId,
        Double latitude,
        Double longitude,
        Instant time
) {}
