package com.demo.courier_gateway_service.service;

import com.demo.courier_gateway_service.client.DistanceServiceClient;
import com.demo.courier_gateway_service.client.StoreServiceClient;
import com.demo.courier_gateway_service.model.dto.CourierLocationEventDTO;
import com.demo.courier_gateway_service.model.response.CourierStoreEntriesResponse;
import com.demo.courier_gateway_service.model.response.CourierTotalDistanceResponse;
import com.demo.courier_gateway_service.producer.CourierLocationPublisher;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.Instant;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class CourierLocationServiceTest {

    private static final Instant EVENT_TIME = Instant.parse("2024-07-03T13:46:40Z");

    @Mock
    private CourierLocationPublisher locationPublisher;

    @Mock
    private DistanceServiceClient distanceServiceClient;

    @Mock
    private StoreServiceClient storeServiceClient;

    @InjectMocks
    private CourierLocationService courierLocationService;

    @Test
    void processLocation_ValidEvent_ShouldPublishToKafka() {
        var event = new CourierLocationEventDTO(
                "courier1_1720000000",
                "courier1",
                40.9923307,
                29.1244229,
                EVENT_TIME.toString()
        );

        courierLocationService.processLocation(event);

        verify(locationPublisher, times(1)).publish(event);
    }

    @Test
    void getTotalDistance_ValidCourierId_ShouldProxyToDistanceService() {
        var response = new CourierTotalDistanceResponse("courier-1", "Test Courier 1", 1250.5);
        when(distanceServiceClient.getTotalDistance("courier-1")).thenReturn(response);

        var result = courierLocationService.getTotalDistance("courier-1");

        assertEquals(response, result);
        verify(distanceServiceClient).getTotalDistance("courier-1");
    }

    @Test
    void getStoreEntries_ValidCourierId_ShouldProxyToStoreService() {
        var response = new CourierStoreEntriesResponse("courier-1", "Test Courier 1", List.of());
        when(storeServiceClient.getStoreEntries("courier-1")).thenReturn(response);

        var result = courierLocationService.getStoreEntries("courier-1");

        assertEquals(response, result);
        verify(storeServiceClient).getStoreEntries("courier-1");
    }
}
