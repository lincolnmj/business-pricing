package com.inditex.pricing.unit.adapter.in.web;

import com.inditex.pricing.adapter.in.web.PriceController;
import com.inditex.pricing.adapter.in.web.mapper.PriceMapper;
import com.inditex.pricing.adapter.in.web.model.PriceResponse;
import com.inditex.pricing.application.port.in.RetrievePriceUseCase;
import com.inditex.pricing.domain.model.Brand;
import com.inditex.pricing.domain.model.Price;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Mono;
import reactor.test.StepVerifier;
import java.time.LocalDateTime;
import static org.mockito.Mockito.*;

class PriceControllerTest {

    private RetrievePriceUseCase useCase;
    private PriceMapper mapper;
    private PriceController controller;
    private ServerWebExchange exchange;

    private final LocalDateTime date = LocalDateTime.now();
    private final Long brandId = 1L;
    private final Long productId = 35455L;

    private Price domainPrice;
    private PriceResponse dtoResponse;

    @BeforeEach
    void setUp() {
        useCase = mock(RetrievePriceUseCase.class);
        mapper = mock(PriceMapper.class);
        exchange = mock(ServerWebExchange.class);
        controller = new PriceController(useCase, mapper);

        domainPrice = new Price(
                new Brand(brandId, "ZARA"),
                productId,
                1,
                1,
                date.minusDays(1),
                date.plusDays(1),
                Double.valueOf("45.99"),
                "EUR"
        );

        dtoResponse = new PriceResponse();
        dtoResponse.setBrandId(brandId);
        dtoResponse.setProductId(productId);
        dtoResponse.setPrice(domainPrice.getPrice());
        dtoResponse.setStartDate(domainPrice.getStartDate().toString());
        dtoResponse.setEndDate(domainPrice.getEndDate().toString());
        dtoResponse.setPriceList(domainPrice.getPriceList());
    }

    @Test
    void shouldReturnPriceResponse_whenUseCaseReturnsPrice() {
        when(useCase.retrievePrice(date, brandId, productId))
                .thenReturn(Mono.just(domainPrice));
        when(mapper.toDto(domainPrice))
                .thenReturn(dtoResponse);

        StepVerifier.create(controller.getPrices(date.toString(), brandId, productId, exchange))
                .expectNextMatches(response ->
                        response.getStatusCode().is2xxSuccessful()
                                && response.getBody() != null
                                && response.getBody().getProductId().equals(productId)
                                && response.getBody().getBrandId().equals(brandId)
                )
                .verifyComplete();

        verify(useCase).retrievePrice(date, brandId, productId);
        verify(mapper).toDto(domainPrice);
    }

    @Test
    void shouldReturnNoContent_whenUseCaseReturnsEmpty() {
        when(useCase.retrievePrice(date, brandId, productId))
                .thenReturn(Mono.empty());

        StepVerifier.create(controller.getPrices(date.toString(), brandId, productId, exchange))
                .expectNextMatches(response ->
                        response.getStatusCode().is2xxSuccessful()
                                && response.getStatusCodeValue() == 204
                                && response.getBody() == null
                )
                .verifyComplete();

        verify(useCase).retrievePrice(date, brandId, productId);
        verifyNoInteractions(mapper);
    }
}