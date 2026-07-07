package com.demo.store_service.strategy;

import com.demo.store_service.model.Location;

public interface DistanceStrategy {

    double calculate(Location from, Location to);

    String getStrategyName();
}
