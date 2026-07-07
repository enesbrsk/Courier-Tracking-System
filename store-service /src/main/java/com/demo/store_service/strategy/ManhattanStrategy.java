package com.demo.store_service.strategy;

import com.demo.store_service.model.Location;
import org.springframework.stereotype.Component;

@Component
public class ManhattanStrategy implements DistanceStrategy {

    private static final double METERS_PER_DEGREE_LAT = 111_320;

    @Override
    public double calculate(Location from, Location to) {
        double latMeters = (to.latitude() - from.latitude()) * METERS_PER_DEGREE_LAT;
        double avgLatRadians = Math.toRadians((from.latitude() + to.latitude()) / 2);
        double lngMeters = (to.longitude() - from.longitude()) * METERS_PER_DEGREE_LAT * Math.cos(avgLatRadians);

        return Math.abs(latMeters) + Math.abs(lngMeters);
    }

    @Override
    public String getStrategyName() {
        return "manhattan";
    }
}
