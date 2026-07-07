package com.demo.distance_service.service;

import com.demo.distance_service.config.DistanceConfig;
import com.demo.distance_service.model.Location;
import com.demo.distance_service.strategy.DistanceStrategy;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class DistanceService {

    private final DistanceConfig distanceConfig;
    private final List<DistanceStrategy> strategies;

    public DistanceService(DistanceConfig distanceConfig, List<DistanceStrategy> strategies) {
        this.distanceConfig = distanceConfig;
        this.strategies = strategies;
    }

    public double calculateMeters(double fromLat, double fromLng, double toLat, double toLng) {
        return resolveStrategy().calculate(
                new Location(fromLat, fromLng),
                new Location(toLat, toLng));
    }

    private DistanceStrategy resolveStrategy() {
        return strategies.stream()
                .filter(strategy -> strategy.getStrategyName().equals(distanceConfig.strategy()))
                .findFirst()
                .orElseThrow(() -> new IllegalStateException(
                        "No distance strategy registered for: " + distanceConfig.strategy()));
    }
}
