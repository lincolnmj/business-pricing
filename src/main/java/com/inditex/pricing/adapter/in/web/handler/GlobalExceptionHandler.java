package com.inditex.pricing.adapter.in.web.handler;

import com.inditex.pricing.domain.exception.PriceNotFoundException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.time.LocalDateTime;
import java.time.format.DateTimeParseException;
import com.inditex.pricing.adapter.in.web.model.ApiErrorResponse;


@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(PriceNotFoundException.class)
    public ResponseEntity<ApiErrorResponse> handlePriceNotFoundException(PriceNotFoundException ex) {
        ApiErrorResponse response = new ApiErrorResponse();
        response.setStatus(204);
        response.setMessage(ex.getMessage());
        response.setTimestamp(String.valueOf(LocalDateTime.now()));
        return ResponseEntity
                .status(HttpStatus.NO_CONTENT)
                .body(response);
    }

    @ExceptionHandler(DateTimeParseException.class)
    public ResponseEntity<ApiErrorResponse> handleDateTimeParseException(DateTimeParseException ex) {
        ApiErrorResponse response = new ApiErrorResponse();
        response.setStatus(400);
        response.setMessage(ex.getMessage());
        response.setTimestamp(String.valueOf(LocalDateTime.now()));
        return ResponseEntity
                .status(HttpStatus.BAD_REQUEST)
                .body(response);
    }
}
