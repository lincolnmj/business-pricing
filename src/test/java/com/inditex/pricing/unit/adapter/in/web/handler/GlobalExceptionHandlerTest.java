package com.inditex.pricing.unit.adapter.in.web.handler;

import com.inditex.pricing.adapter.in.web.model.ApiErrorResponse;
import com.inditex.pricing.domain.exception.PriceNotFoundException;
import org.junit.jupiter.api.Test;
import org.springframework.http.ResponseEntity;
import java.time.format.DateTimeParseException;
import com.inditex.pricing.adapter.in.web.handler.GlobalExceptionHandler;
import static org.assertj.core.api.Assertions.assertThat;

class GlobalExceptionHandlerTest {
    private final GlobalExceptionHandler handler = new GlobalExceptionHandler();

    @Test
    void handlePriceNotFoundException_shouldReturnNotFoundResponse() {
        
        Long productId = 35455L;
        Long brandId = 1L;
        String date = "2020-06-14T10:00";
        PriceNotFoundException ex = new PriceNotFoundException(productId, brandId, date);

        
        ResponseEntity<ApiErrorResponse> responseEntity = handler.handlePriceNotFoundException(ex);

        
        assertThat(responseEntity.getStatusCodeValue()).isEqualTo(204);

        ApiErrorResponse response = responseEntity.getBody();
        assertThat(response).isNotNull();
        assertThat(response.getStatus()).isEqualTo(204);
        assertThat(response.getMessage()).contains("productId=35455", "brandId=1");
        assertThat(response.getTimestamp()).isNotEmpty();
    }

    @Test
    void handleDateTimeParseException_shouldReturnBadRequestResponse() {
        
        DateTimeParseException ex = new DateTimeParseException("Text 'invalid-date' could not be parsed", "invalid-date", 0);

        
        ResponseEntity<ApiErrorResponse> responseEntity = handler.handleDateTimeParseException(ex);

        
        assertThat(responseEntity.getStatusCodeValue()).isEqualTo(400);

        ApiErrorResponse response = responseEntity.getBody();
        assertThat(response).isNotNull();
        assertThat(response.getStatus()).isEqualTo(400);
        assertThat(response.getMessage()).contains("invalid-date");
        assertThat(response.getTimestamp()).isNotEmpty();
    }
}
