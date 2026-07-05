package com.demo.distance_service.model.response;

public record CourierTotalDistanceResponse(
        String courierId,
        String courierName,
        double totalDistanceMeters
) {
}
