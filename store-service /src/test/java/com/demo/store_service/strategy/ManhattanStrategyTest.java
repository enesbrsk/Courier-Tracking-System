package com.demo.store_service.strategy;

import com.demo.store_service.model.Location;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

class ManhattanStrategyTest {

    private final ManhattanStrategy strategy = new ManhattanStrategy();

    @Test
    void calculate_SamePoint_ShouldReturnZero() {
        var point = new Location(40.99, 29.12);

        assertEquals(0.0, strategy.calculate(point, point), 0.001);
    }

    @Test
    void calculate_TwoDistinctPoints_ShouldReturnManhattanDistance() {
        var from = new Location(40.9923307, 29.1244229);
        var to = new Location(40.986106, 29.1161293);

        double distance = strategy.calculate(from, to);

        assertTrue(distance > 0);
        assertTrue(distance < 1500.0);
    }

    @Test
    void getStrategyName_ShouldReturnManhattan() {
        assertEquals("manhattan", strategy.getStrategyName());
    }
}
