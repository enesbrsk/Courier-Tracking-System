package com.demo.store_service.model.response;

import java.util.List;

public record CourierStoreEntriesResponse(
        String courierId,
        String courierName,
        List<StoreEntryResponse> entries
) {
}
