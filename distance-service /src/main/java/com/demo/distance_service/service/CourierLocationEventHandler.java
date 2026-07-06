package com.demo.distance_service.service;

import com.demo.distance_service.exception.CourierNotFoundException;
import com.demo.distance_service.model.DistanceProperties;
import com.demo.distance_service.model.dto.CourierLocationEventDTO;
import com.demo.distance_service.repository.CourierDistanceRepository;
import com.demo.distance_service.repository.redis.CachedCourierLastLocation;
import com.demo.distance_service.repository.redis.CachedLastLocationRepository;
import com.demo.distance_service.service.calculator.DistanceCalculator;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Slf4j
@Service
@RequiredArgsConstructor
public class CourierLocationEventHandler {

    private final CachedLastLocationRepository cachedLastLocationRepository;
    private final CourierDistanceRepository courierDistanceRepository;
    private final DistanceCalculator distanceCalculator;
    private final DistanceProperties distanceProperties;

    @Transactional
    public void processCourierLocation(CourierLocationEventDTO event) {
        Optional<CachedCourierLastLocation> previousCourierLocation = resolvePreviousLocation(event.courierId());

        var courier = courierDistanceRepository.findByCourier_Id(event.courierId())
                .orElseThrow(() -> new CourierNotFoundException(event.courierId()));

        if (previousCourierLocation.isPresent()) {
            CachedCourierLastLocation last = previousCourierLocation.get();
            double distance = distanceCalculator.calculateMeters(
                    last.getLatitude(), last.getLongitude(),
                    event.latitude(), event.longitude());

            if (distance >= distanceProperties.minMovementThresholdMeters()) {
                courier.addDistance(distance);
                log.info("Distance added. courierId={}, meters={}", event.courierId(), distance);
            }
        }

        courier.updateLastLocation(event.latitude(), event.longitude(), event.time());
        courierDistanceRepository.save(courier);
        cachedLastLocationRepository.save(new CachedCourierLastLocation(
                event.courierId(), event.latitude(), event.longitude(), event.time()));
    }

    private Optional<CachedCourierLastLocation> resolvePreviousLocation(String courierId) {
        return cachedLastLocationRepository.findById(courierId)
                .or(() -> courierDistanceRepository.findByCourier_Id(courierId)
                        .filter(entity -> entity.getLastLatitude() != null)
                        .map(entity -> {
                            var cached = new CachedCourierLastLocation(
                                    courierId,
                                    entity.getLastLatitude(),
                                    entity.getLastLongitude(),
                                    entity.getLastTime());
                            cachedLastLocationRepository.save(cached);
                            return cached;
                        }));
    }
}
