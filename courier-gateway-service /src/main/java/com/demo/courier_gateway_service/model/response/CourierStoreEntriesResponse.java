package com.demo.courier_gateway_service.model.response;

import java.util.List;

public record CourierStoreEntriesResponse(
        String courierId,
        String courierName,
        List<StoreEntryResponse> entries
) {
}
