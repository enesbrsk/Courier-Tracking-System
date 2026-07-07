package com.demo.courier_gateway_service.model.response;

public record ErrorResponse(
        String message,
        String code,
        int status
) {
}
