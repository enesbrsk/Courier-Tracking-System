package com.demo.distance_service.service;

import com.demo.distance_service.exception.CourierNotFoundException;
import com.demo.distance_service.model.DistanceProperties;
import com.demo.distance_service.model.dto.CourierLocationEventDTO;
import com.demo.distance_service.repository.CourierDistanceRepository;
import com.demo.distance_service.service.calculator.DistanceCalculator;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Slf4j
@Service
@RequiredArgsConstructor
public class CourierLocationEventHandler {

    private final CourierDistanceRepository courierDistanceRepository;
    private final DistanceCalculator distanceCalculator;
    private final DistanceProperties distanceProperties;

    @Transactional
    public void processCourierLocation(CourierLocationEventDTO event) {
        var courier = courierDistanceRepository.findByCourier_Id(event.courierId())
                .orElseThrow(() -> new CourierNotFoundException(event.courierId()));

        if (courier.hasLastKnownLocation()) {
            double distance = distanceCalculator.calculateMeters(
                    courier.getLastLatitude(), courier.getLastLongitude(),
                    event.latitude(), event.longitude());

            if (distance >= distanceProperties.minMovementThresholdMeters()) {
                courier.addDistance(distance);
                log.info("Distance added. courierId={}, meters={}", event.courierId(), distance);
            }
        }

        courier.updateLastLocation(event.latitude(), event.longitude(), event.time());
        courierDistanceRepository.save(courier);
    }
}
