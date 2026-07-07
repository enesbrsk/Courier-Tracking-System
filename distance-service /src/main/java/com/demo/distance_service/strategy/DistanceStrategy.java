package com.demo.distance_service.strategy;

import com.demo.distance_service.model.Location;

public interface DistanceStrategy {

    double calculate(Location from, Location to);

    String getStrategyName();
}
