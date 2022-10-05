package com.project.ricky.common.exception;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

public class BadRequestException extends RuntimeException{

    private static final long serialVersionUID = 1L;

    private final String message;

    public BadRequestException(String message, HttpStatus httpStatus) {
        this.message = message;
    }

    @Override
    public String getMessage() {
        return message;
    }

    public ResponseEntity<?> getHttpStatus() {
        return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);
    }


}
