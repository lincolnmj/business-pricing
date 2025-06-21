package com.inditex.pricing.unit.application.service;

import com.inditex.pricing.application.port.out.RetrievePriceRepositoryPort;
import com.inditex.pricing.application.service.RetrievePriceService;
import com.inditex.pricing.domain.exception.PriceNotFoundException;
import com.inditex.pricing.domain.model.Price;
import com.inditex.pricing.domain.model.Brand;
import com.inditex.pricing.domain.service.PriceSelectorService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import reactor.core.publisher.Flux;
import reactor.test.StepVerifier;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import static org.mockito.Mockito.*;

class RetrievePriceServiceTest {

    private RetrievePriceRepositoryPort repositoryPort;
    private PriceSelectorService priceSelector;
    private RetrievePriceService service;

    private final LocalDateTime date = LocalDateTime.now();
    private final Long productId = 35455L;
    private final Long brandId = 1L;

    private Price samplePrice;

    @BeforeEach
    void setUp() {
        repositoryPort = mock(RetrievePriceRepositoryPort.class);
        priceSelector = mock(PriceSelectorService.class);
        service = new RetrievePriceService(repositoryPort, priceSelector);

        samplePrice = new Price(
                new Brand(brandId, "ZARA"),
                productId,
                1,
                1,
                date.minusDays(1),
                date.plusDays(1),
                Double.valueOf("35.50"),
                "EUR"
        );
    }

    @Test
    void shouldReturnPrice_whenApplicablePriceFound() {
        when(repositoryPort.findAllPrices(date, brandId, productId))
                .thenReturn(Flux.just(samplePrice));

        when(priceSelector.selectApplicablePrice(List.of(samplePrice)))
                .thenReturn(Optional.of(samplePrice));

        StepVerifier.create(service.retrievePrice(date, brandId, productId))
                .expectNext(samplePrice)
                .verifyComplete();

        verify(repositoryPort).findAllPrices(date, brandId, productId);
        verify(priceSelector).selectApplicablePrice(List.of(samplePrice));
    }

    @Test
    void shouldThrowException_whenNoApplicablePriceFound() {
        when(repositoryPort.findAllPrices(date, brandId, productId))
                .thenReturn(Flux.just(samplePrice));

        when(priceSelector.selectApplicablePrice(List.of(samplePrice)))
                .thenReturn(Optional.empty());

        StepVerifier.create(service.retrievePrice(date, brandId, productId))
                .expectErrorMatches(throwable ->
                        throwable instanceof PriceNotFoundException &&
                                throwable.getMessage().contains("productId=35455"))
                .verify();

        verify(repositoryPort).findAllPrices(date, brandId, productId);
        verify(priceSelector).selectApplicablePrice(List.of(samplePrice));
    }
}