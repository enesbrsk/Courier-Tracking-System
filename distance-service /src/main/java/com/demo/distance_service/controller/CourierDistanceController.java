package com.demo.distance_service.controller;

import com.demo.distance_service.model.response.CourierTotalDistanceResponse;
import com.demo.distance_service.service.DistanceQueryService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/couriers")
public class CourierDistanceController {

    private final DistanceQueryService distanceQueryService;

    @GetMapping("/{courierId}/total-distance")
    public ResponseEntity<CourierTotalDistanceResponse> getTotalDistance(@PathVariable String courierId) {
        return ResponseEntity.ok(distanceQueryService.getTotalDistance(courierId));
    }
}
