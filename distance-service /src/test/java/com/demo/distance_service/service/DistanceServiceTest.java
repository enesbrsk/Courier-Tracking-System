package com.demo.distance_service.service;

import com.demo.distance_service.config.DistanceConfig;
import com.demo.distance_service.strategy.HaversineStrategy;
import com.demo.distance_service.strategy.ManhattanStrategy;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

class DistanceServiceTest {

    private final HaversineStrategy haversineStrategy = new HaversineStrategy();
    private final ManhattanStrategy manhattanStrategy = new ManhattanStrategy();

    @Test
    void calculateMeters_HaversineConfig_ShouldUseHaversineStrategy() {
        var config = mock(DistanceConfig.class);
        when(config.strategy()).thenReturn("haversine");
        var service = new DistanceService(config, List.of(haversineStrategy, manhattanStrategy));

        double distance = service.calculateMeters(40.9923307, 29.1244229, 40.986106, 29.1161293);

        assertEquals(haversineStrategy.calculate(
                new com.demo.distance_service.model.Location(40.9923307, 29.1244229),
                new com.demo.distance_service.model.Location(40.986106, 29.1161293)),
                distance,
                0.001);
    }

    @Test
    void calculateMeters_ManhattanConfig_ShouldUseManhattanStrategy() {
        var config = mock(DistanceConfig.class);
        when(config.strategy()).thenReturn("manhattan");
        var service = new DistanceService(config, List.of(haversineStrategy, manhattanStrategy));

        double distance = service.calculateMeters(40.9923307, 29.1244229, 40.986106, 29.1161293);

        assertEquals(manhattanStrategy.calculate(
                new com.demo.distance_service.model.Location(40.9923307, 29.1244229),
                new com.demo.distance_service.model.Location(40.986106, 29.1161293)),
                distance,
                0.001);
    }

    @Test
    void calculateMeters_UnknownStrategy_ShouldThrowException() {
        var config = mock(DistanceConfig.class);
        when(config.strategy()).thenReturn("vincenty");
        var service = new DistanceService(config, List.of(haversineStrategy, manhattanStrategy));

        assertThrows(IllegalStateException.class,
                () -> service.calculateMeters(40.99, 29.12, 41.0, 29.13));
    }
}
