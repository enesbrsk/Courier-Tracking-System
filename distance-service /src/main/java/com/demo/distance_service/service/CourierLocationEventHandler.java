package com.demo.distance_service.service;

import com.demo.distance_service.config.DistanceConfig;
import com.demo.distance_service.exception.CourierNotFoundException;
import com.demo.distance_service.model.dto.CourierLocationEventDTO;
import com.demo.distance_service.repository.CourierDistanceEntity;
import com.demo.distance_service.repository.CourierDistanceRepository;
import com.demo.distance_service.repository.CourierRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Slf4j
@Service
@RequiredArgsConstructor
public class CourierLocationEventHandler {

    private final CourierRepository courierRepository;
    private final CourierDistanceRepository courierDistanceRepository;
    private final DistanceService distanceService;
    private final DistanceConfig distanceConfig;

    @Transactional
    public void processCourierLocation(CourierLocationEventDTO event) {
        var courierEntity = courierRepository.findById(event.courierId())
                .orElseThrow(() -> new CourierNotFoundException(event.courierId()));

        var courier = courierDistanceRepository.findByCourier_Id(event.courierId())
                .orElseGet(() -> new CourierDistanceEntity(courierEntity));

        if (courier.hasLastKnownLocation()) {
            double distance = distanceService.calculateMeters(
                    courier.getLastLatitude(), courier.getLastLongitude(),
                    event.latitude(), event.longitude());

            if (distance >= distanceConfig.minMovementThresholdMeters()) {
                courier.addDistance(distance);
                log.info("Distance added. courierId={}, meters={}", event.courierId(), distance);
            }
        }

        courier.updateLastLocation(event.latitude(), event.longitude(), event.eventTime());
        courierDistanceRepository.save(courier);
    }
}
