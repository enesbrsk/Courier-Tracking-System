package com.demo.courier_gateway_service.model.response;

import java.time.Instant;

public record StoreEntryResponse(
        String storeName,
        Instant time
) {
}
