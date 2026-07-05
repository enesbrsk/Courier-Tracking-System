package com.demo.distance_service.exception;

import com.demo.distance_service.model.response.ErrorResponse;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(CourierNotFoundException.class)
    public ResponseEntity<ErrorResponse> handleCourierNotFoundException(CourierNotFoundException ex) {
        ErrorResponse error = ErrorResponse.builder()
                .message(ex.getMessage())
                .code("COURIER_NOT_FOUND")
                .status(HttpStatus.NOT_FOUND.value())
                .build();
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(error);
    }
}
