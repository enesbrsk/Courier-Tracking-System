package com.demo.courier_gateway_service.model.dto;

import com.demo.courier_gateway_service.model.request.CourierLocationRequest;

public record CourierLocationEventDTO(
        String eventId,
        String courier,
        Double latitude,
        Double longitude,
        Long timestamp
) {
    public static CourierLocationEventDTO fromRequest(CourierLocationRequest request) {
        String deterministicEventId = request.courier() + "_" + request.timestamp();

        return new CourierLocationEventDTO(
                deterministicEventId,
                request.courier(),
                request.latitude(),
                request.longitude(),
                request.timestamp()
        );
    }
}
