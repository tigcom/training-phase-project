package com.example.product_service.response;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class ApiResponseWrapper<T> {
    private int status;

    private String message;

    private T data;
}