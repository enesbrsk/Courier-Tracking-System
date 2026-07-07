package com.demo.store_service.controller;

import com.demo.store_service.exception.CourierNotFoundException;
import com.demo.store_service.exception.GlobalExceptionHandler;
import com.demo.store_service.model.response.CourierStoreEntriesResponse;
import com.demo.store_service.model.response.StoreEntryResponse;
import com.demo.store_service.service.StoreEntryQueryService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.webmvc.test.autoconfigure.WebMvcTest;
import org.springframework.context.annotation.Import;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;

import java.time.Instant;
import java.util.List;

import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(CourierStoreEntryController.class)
@Import(GlobalExceptionHandler.class)
@ActiveProfiles("test")
class CourierStoreEntryControllerTest {

    private static final Instant EVENT_TIME = Instant.parse("2024-07-03T13:46:40Z");

    @Autowired
    MockMvc mockMvc;

    @MockitoBean
    StoreEntryQueryService storeEntryQueryService;

    @Test
    void getStoreEntries_KnownCourier_ShouldReturn200WithEntries() throws Exception {
        when(storeEntryQueryService.getStoreEntries("courier-1"))
                .thenReturn(new CourierStoreEntriesResponse(
                        "courier-1",
                        "Test Courier 1",
                        List.of(new StoreEntryResponse("Ataşehir MMM Migros", EVENT_TIME))));

        mockMvc.perform(get("/api/v1/couriers/courier-1/store-entries"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.courierId").value("courier-1"))
                .andExpect(jsonPath("$.courierName").value("Test Courier 1"))
                .andExpect(jsonPath("$.entries[0].storeName").value("Ataşehir MMM Migros"))
                .andExpect(jsonPath("$.entries[0].time").value("2024-07-03T13:46:40Z"));
    }

    @Test
    void getStoreEntries_UnknownCourier_ShouldReturn404() throws Exception {
        when(storeEntryQueryService.getStoreEntries("unknown"))
                .thenThrow(new CourierNotFoundException("unknown"));

        mockMvc.perform(get("/api/v1/couriers/unknown/store-entries"))
                .andExpect(status().isNotFound())
                .andExpect(jsonPath("$.message").value("Courier not found: unknown"))
                .andExpect(jsonPath("$.code").value("COURIER_NOT_FOUND"))
                .andExpect(jsonPath("$.status").value(404));
    }
}
