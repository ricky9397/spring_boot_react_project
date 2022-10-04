package com.project.ricky.common.exception;

import lombok.extern.slf4j.Slf4j;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
@Slf4j
public class ExceptionHandler {

    private Logger logger = LoggerFactory.getLogger(this.getClass());

    public ResponseEntity<?> error401() {
        return new ResponseEntity(HttpStatus.UNAUTHORIZED);
    }
}
