package com.demo.courier_gateway_service.model.response;

public record CourierTotalDistanceResponse(
        String courierId,
        String courierName,
        Double totalDistanceMeters
) {}
