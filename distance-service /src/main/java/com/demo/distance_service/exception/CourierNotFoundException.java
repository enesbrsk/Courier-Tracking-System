package com.demo.distance_service.exception;

public class CourierNotFoundException extends RuntimeException {

    public CourierNotFoundException(String courierId) {
        super("Courier not found: " + courierId);
    }
}
