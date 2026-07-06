package com.demo.courier_gateway_service.model.dto;

import com.demo.courier_gateway_service.model.request.CourierLocationRequest;

public record CourierLocationEventDTO(
        String eventId,
        String courierId,
        Double latitude,
        Double longitude,
        String time
) {
    public static CourierLocationEventDTO fromRequest(CourierLocationRequest request) {
        String deterministicEventId = request.courierId() + "_" + request.time().toEpochMilli();

        return new CourierLocationEventDTO(
                deterministicEventId,
                request.courierId(),
                request.latitude(),
                request.longitude(),
                request.time().toString()
        );
    }
}
