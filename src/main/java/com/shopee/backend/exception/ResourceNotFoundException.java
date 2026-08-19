package com.shopee.backend.exception;

import org.springframework.http.HttpStatus;

public class ResourceNotFoundException extends BusinessException{
    public ResourceNotFoundException(String message) {
        super(message, HttpStatus.NOT_FOUND);
    }

    public ResourceNotFoundException(String resource, Object id) {
        super("Khong tim thay " + resource + "id = " + id, HttpStatus.NOT_FOUND);
    }
}
