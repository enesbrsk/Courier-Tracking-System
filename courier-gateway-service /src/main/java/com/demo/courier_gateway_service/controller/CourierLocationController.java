package com.demo.courier_gateway_service.controller;

import com.demo.courier_gateway_service.model.dto.CourierLocationEventDTO;
import com.demo.courier_gateway_service.model.request.CourierLocationRequest;
import com.demo.courier_gateway_service.model.response.CourierStoreEntriesResponse;
import com.demo.courier_gateway_service.model.response.CourierTotalDistanceResponse;
import com.demo.courier_gateway_service.service.CourierLocationService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/courier-locations")
@Tag(name = "Courier Locations", description = "Location ingestion and courier query APIs")
public class CourierLocationController {

    private final CourierLocationService courierLocationService;

    @PostMapping
    @Operation(summary = "Submit courier location", description = "Accepts a location ping and publishes it to Kafka for async processing.")
    public ResponseEntity<Void> receiveLocation(@Valid @RequestBody CourierLocationRequest request) {
        courierLocationService.processLocation(CourierLocationEventDTO.fromRequest(request));
        return ResponseEntity.status(HttpStatus.ACCEPTED).build();
    }

    @GetMapping("/{courierId}/total-distance")
    @Operation(summary = "Get total travel distance", description = "Proxies the query to distance-service.")
    public ResponseEntity<CourierTotalDistanceResponse> getTotalDistance(@PathVariable String courierId) {
        return ResponseEntity.ok(courierLocationService.getTotalDistance(courierId));
    }

    @GetMapping("/{courierId}/store-entries")
    @Operation(summary = "Get store entries", description = "Proxies the query to store-service.")
    public ResponseEntity<CourierStoreEntriesResponse> getStoreEntries(@PathVariable String courierId) {
        return ResponseEntity.ok(courierLocationService.getStoreEntries(courierId));
    }
}
