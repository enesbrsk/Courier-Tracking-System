package com.demo.courier_gateway_service.model.request;

import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;

public record CourierLocationRequest(
        @NotNull(message = "Courier cannot be null")
        String courier,

        @NotNull(message = "Latitude cannot be null")
        @Min(value = -90, message = "Latitude must be between -90 and 90")
        @Max(value = 90, message = "Latitude must be between -90 and 90")
        Double latitude,

        @NotNull(message = "Longitude cannot be null")
        @Min(value = -180, message = "Longitude must be between -180 and 180")
        @Max(value = 180, message = "Longitude must be between -180 and 180")
        Double longitude,

        @NotNull(message = "Timestamp cannot be null")
        Long timestamp
) {}
