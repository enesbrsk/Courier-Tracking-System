package com.demo.distance_service.service;

import com.demo.distance_service.exception.CourierNotFoundException;
import com.demo.distance_service.model.response.CourierTotalDistanceResponse;
import com.demo.distance_service.repository.CourierRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class DistanceQueryService {

    private final CourierRepository courierRepository;

    public CourierTotalDistanceResponse getTotalDistance(String courierId) {
        var courier = courierRepository.findByIdWithDistance(courierId)
                .orElseThrow(() -> new CourierNotFoundException(courierId));

        double totalDistanceMeters = courier.getDistance() != null
                ? courier.getDistance().getTotalDistance()
                : 0.0;

        return new CourierTotalDistanceResponse(courierId, courier.getName(), totalDistanceMeters);
    }
}
