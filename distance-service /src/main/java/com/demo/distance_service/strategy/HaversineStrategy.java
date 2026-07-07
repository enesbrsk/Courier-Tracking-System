package com.demo.distance_service.strategy;

import com.demo.distance_service.model.Location;
import org.springframework.stereotype.Component;

@Component
public class HaversineStrategy implements DistanceStrategy {

    private static final double EARTH_RADIUS_METERS = 6_371_000;

    @Override
    public double calculate(Location from, Location to) {
        double latDistance = Math.toRadians(to.latitude() - from.latitude());
        double lngDistance = Math.toRadians(to.longitude() - from.longitude());

        double a = Math.sin(latDistance / 2) * Math.sin(latDistance / 2)
                + Math.cos(Math.toRadians(from.latitude())) * Math.cos(Math.toRadians(to.latitude()))
                * Math.sin(lngDistance / 2) * Math.sin(lngDistance / 2);

        double c = 2 * Math.atan2(Math.sqrt(a), Math.sqrt(1 - a));
        return EARTH_RADIUS_METERS * c;
    }

    @Override
    public String getStrategyName() {
        return "haversine";
    }
}
