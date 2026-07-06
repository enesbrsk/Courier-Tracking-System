package com.demo.courier_gateway_service.client;

import com.demo.courier_gateway_service.model.response.CourierTotalDistanceResponse;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@FeignClient(
        name = "distance-service",
        url = "${app.distance-service.url}"
)
public interface DistanceServiceClient {

    @GetMapping("/api/v1/couriers/{courierId}/total-distance")
    CourierTotalDistanceResponse getTotalDistance(@PathVariable String courierId);
}
