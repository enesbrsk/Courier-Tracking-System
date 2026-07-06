package com.demo.store_service.controller;

import com.demo.store_service.model.response.CourierStoreEntriesResponse;
import com.demo.store_service.service.StoreEntryQueryService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/couriers")
public class CourierStoreEntryController {

    private final StoreEntryQueryService storeEntryQueryService;

    @GetMapping("/{courierId}/store-entries")
    public ResponseEntity<CourierStoreEntriesResponse> getStoreEntries(@PathVariable String courierId) {
        return ResponseEntity.ok(storeEntryQueryService.getStoreEntries(courierId));
    }
}
