package com.inditex.pricing.integration.application.port;

import com.inditex.pricing.application.port.ConsultPriceService;
import com.inditex.pricing.application.port.out.PriceRetrievalPort;
import com.inditex.pricing.domain.exception.PriceNotFoundException;
import com.inditex.pricing.domain.model.Brand;
import com.inditex.pricing.domain.model.Price;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import reactor.core.publisher.Mono;
import reactor.test.StepVerifier;

import java.math.BigDecimal;
import java.time.LocalDateTime;

import static org.mockito.Mockito.*;

class ConsultPriceServiceTest {
    private PriceRetrievalPort priceRetrievalPort;
    private ConsultPriceService consultPriceService;

    @BeforeEach
    void setup() {
        priceRetrievalPort = mock(PriceRetrievalPort.class);
        consultPriceService = new ConsultPriceService(priceRetrievalPort);
    }

    @Test
    void shouldReturnPriceWhenFound() {
        LocalDateTime date = LocalDateTime.of(2020, 6, 14, 10, 0);
        Long brandId = 1L;
        Long productId = 35455L;
        Brand expectedBrand = new Brand(brandId, "ZARA");
        Price expectedPrice = new Price(expectedBrand, productId, 1, 1, date, date.plusDays(1), BigDecimal.valueOf(25.5), "EUR");

        when(priceRetrievalPort.retrievePriceToApply(date, brandId, productId))
                .thenReturn(Mono.just(expectedPrice));

        StepVerifier.create(consultPriceService.consultPrice(date, brandId, productId))
                .expectNext(expectedPrice)
                .verifyComplete();

        verify(priceRetrievalPort).retrievePriceToApply(date, brandId, productId);
    }

    @Test
    void shouldThrowExceptionWhenPriceNotFound() {
        LocalDateTime date = LocalDateTime.of(2020, 6, 14, 23, 0);
        Long brandId = 1L;
        Long productId = 35455L;

        when(priceRetrievalPort.retrievePriceToApply(date, brandId, productId))
                .thenReturn(Mono.empty());

        StepVerifier.create(consultPriceService.consultPrice(date, brandId, productId))
                .expectErrorMatches(throwable ->
                        throwable instanceof PriceNotFoundException &&
                                throwable.getMessage().contains("No se encontró una tarifa aplicable")
                )
                .verify();

        verify(priceRetrievalPort).retrievePriceToApply(date, brandId, productId);
    }
}