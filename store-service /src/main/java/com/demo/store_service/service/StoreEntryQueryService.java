package com.demo.store_service.service;

import com.demo.store_service.exception.CourierNotFoundException;
import com.demo.store_service.model.response.CourierStoreEntriesResponse;
import com.demo.store_service.model.response.StoreEntryResponse;
import com.demo.store_service.repository.CourierRepository;
import com.demo.store_service.repository.CourierStoreEntryRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class StoreEntryQueryService {

    private final CourierRepository courierRepository;
    private final CourierStoreEntryRepository courierStoreEntryRepository;

    public CourierStoreEntriesResponse getStoreEntries(String courierId) {
        var courier = courierRepository.findById(courierId)
                .orElseThrow(() -> new CourierNotFoundException(courierId));

        var entries = courierStoreEntryRepository.findByCourier_IdOrderByClientTimestampDesc(courierId)
                .stream()
                .map(entry -> new StoreEntryResponse(entry.getStoreName(), entry.getClientTimestamp()))
                .toList();

        return new CourierStoreEntriesResponse(courierId, courier.getName(), entries);
    }
}
