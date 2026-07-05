package com.demo.distance_service.service.calculator;

public interface DistanceCalculator {
    double calculateMeters(double fromLat, double fromLng, double toLat, double toLng);
}
