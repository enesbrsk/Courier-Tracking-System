package com.demo.courier_gateway_service.controller;

import com.demo.courier_gateway_service.model.dto.CourierLocationEventDTO;
import com.demo.courier_gateway_service.model.request.CourierLocationRequest;
import com.demo.courier_gateway_service.service.CourierLocationService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/courier-locations")
public class CourierLocationController {

    private final CourierLocationService courierLocationService;

    @PostMapping
    public ResponseEntity<Void> receiveLocation(@Valid @RequestBody CourierLocationRequest request) {
        courierLocationService.processLocation(CourierLocationEventDTO.fromRequest(request));
        return ResponseEntity.status(HttpStatus.ACCEPTED).build();
    }
}