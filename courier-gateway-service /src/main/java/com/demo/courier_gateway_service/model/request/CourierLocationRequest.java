package com.demo.courier_gateway_service.model.request;

import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

import java.time.Instant;

public record CourierLocationRequest(
        @NotBlank(message = "Courier ID cannot be blank")
        String courierId,

        @NotNull(message = "Latitude cannot be null")
        @Min(value = -90, message = "Latitude must be between -90 and 90")
        @Max(value = 90, message = "Latitude must be between -90 and 90")
        Double latitude,

        @NotNull(message = "Longitude cannot be null")
        @Min(value = -180, message = "Longitude must be between -180 and 180")
        @Max(value = 180, message = "Longitude must be between -180 and 180")
        Double longitude,

        @NotNull(message = "Time cannot be null")
        Instant time
) {}
