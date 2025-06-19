package com.inditex.pricing.unit.domain.exception;

import com.inditex.pricing.domain.exception.GlobalExceptionHandler;
import com.inditex.pricing.domain.exception.PriceNotFoundException;
import org.junit.jupiter.api.Test;
import org.springframework.http.ResponseEntity;

import static org.junit.jupiter.api.Assertions.*;

class GlobalExceptionHandlerTest {

    @Test
    void shouldReturnNoContentForPriceNotFoundException() {
        GlobalExceptionHandler handler = new GlobalExceptionHandler();
        PriceNotFoundException ex = new PriceNotFoundException(35455L, 1L, "2020-06-14T21:00:00");

        ResponseEntity<Void> response = handler.handlePriceNotFoundException(ex);

        assertNotNull(response);
        assertEquals(204, response.getStatusCodeValue()); // NO_CONTENT
    }
}