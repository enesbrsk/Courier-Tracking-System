package com.demo.distance_service.controller;

import com.demo.distance_service.exception.CourierNotFoundException;
import com.demo.distance_service.exception.GlobalExceptionHandler;
import com.demo.distance_service.model.response.CourierTotalDistanceResponse;
import com.demo.distance_service.service.DistanceQueryService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.webmvc.test.autoconfigure.WebMvcTest;
import org.springframework.context.annotation.Import;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;

import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(CourierDistanceController.class)
@Import(GlobalExceptionHandler.class)
@ActiveProfiles("test")
class CourierDistanceControllerTest {

    @Autowired
    MockMvc mockMvc;

    @MockitoBean
    DistanceQueryService distanceQueryService;

    @Test
    void getTotalDistance_KnownCourier_ShouldReturn200WithDistance() throws Exception {
        when(distanceQueryService.getTotalDistance("courier-1"))
                .thenReturn(new CourierTotalDistanceResponse("courier-1", "Test Courier 1", 1234.56));

        mockMvc.perform(get("/api/v1/couriers/courier-1/total-distance"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.courierId").value("courier-1"))
                .andExpect(jsonPath("$.courierName").value("Test Courier 1"))
                .andExpect(jsonPath("$.totalDistanceMeters").value(1234.56));
    }

    @Test
    void getTotalDistance_UnknownCourier_ShouldReturn404() throws Exception {
        when(distanceQueryService.getTotalDistance("unknown"))
                .thenThrow(new CourierNotFoundException("unknown"));

        mockMvc.perform(get("/api/v1/couriers/unknown/total-distance"))
                .andExpect(status().isNotFound())
                .andExpect(jsonPath("$.message").value("Courier not found: unknown"))
                .andExpect(jsonPath("$.code").value("COURIER_NOT_FOUND"))
                .andExpect(jsonPath("$.status").value(404));
    }
}
