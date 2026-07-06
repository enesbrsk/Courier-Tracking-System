package com.demo.store_service.model.response;

public record StoreEntryResponse(
        String storeName,
        long clientTimestamp
) {
}
