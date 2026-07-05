package com.demo.distance_service.model.response;

import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class ErrorResponse {
    private String message;
    private String code;
    private int status;
}
