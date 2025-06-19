package com.inditex.pricing.domain.exception;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(PriceNotFoundException.class)
    public ResponseEntity<Void> handlePriceNotFoundException(PriceNotFoundException ex) {
        return ResponseEntity.noContent().build();
    }
}
