package com.demo.courier_gateway_service.client;

import com.demo.courier_gateway_service.model.response.CourierStoreEntriesResponse;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@FeignClient(
        name = "store-service",
        url = "${app.store-service.url}"
)
public interface StoreServiceClient {

    @GetMapping("/api/v1/couriers/{courierId}/store-entries")
    CourierStoreEntriesResponse getStoreEntries(@PathVariable String courierId);
}
