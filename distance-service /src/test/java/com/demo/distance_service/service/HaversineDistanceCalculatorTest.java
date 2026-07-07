package com.demo.distance_service.service;

import com.demo.distance_service.service.calculator.HaversineDistanceCalculator;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

class HaversineDistanceCalculatorTest {

    private final HaversineDistanceCalculator calculator = new HaversineDistanceCalculator();

    @Test
    void calculateMeters_SamePoint_ShouldReturnZero() {
        assertEquals(0.0, calculator.calculateMeters(40.99, 29.12, 40.99, 29.12), 0.001);
    }

    @Test
    void calculateMeters_TwoDistinctPoints_ShouldReturnDistance() {
        double distance = calculator.calculateMeters(40.9923307, 29.1244229, 40.986106, 29.1161293);
        assertEquals(980.0, distance, 50.0);
    }
}
