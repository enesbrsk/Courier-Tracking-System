package com.demo.courier_gateway_service.service;

import com.demo.courier_gateway_service.client.DistanceServiceClient;
import com.demo.courier_gateway_service.client.StoreServiceClient;
import com.demo.courier_gateway_service.model.dto.CourierLocationEventDTO;
import com.demo.courier_gateway_service.model.response.CourierStoreEntriesResponse;
import com.demo.courier_gateway_service.model.response.CourierTotalDistanceResponse;
import com.demo.courier_gateway_service.producer.CourierLocationPublisher;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Service
@Slf4j
@RequiredArgsConstructor
public class CourierLocationService {

    private final CourierLocationPublisher locationPublisher;
    private final DistanceServiceClient distanceServiceClient;
    private final StoreServiceClient storeServiceClient;

    public void processLocation(CourierLocationEventDTO courierLocationEventDTO) {
        log.info("Gateway received location event. CourierID: {}, Timestamp: {}",
                courierLocationEventDTO.courierId(), courierLocationEventDTO.time());

        locationPublisher.publish(courierLocationEventDTO);
    }

    public CourierTotalDistanceResponse getTotalDistance(String courierId) {
        return distanceServiceClient.getTotalDistance(courierId);
    }

    public CourierStoreEntriesResponse getStoreEntries(String courierId) {
        return storeServiceClient.getStoreEntries(courierId);
    }
}
