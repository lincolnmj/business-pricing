package com.inditex.pricing.unit.adapter.in.web;

import com.inditex.pricing.adapter.in.web.PriceController;
import com.inditex.pricing.application.port.in.RetrievePriceUseCase;
import com.inditex.pricing.domain.model.Brand;
import com.inditex.pricing.domain.model.Price;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.ResponseEntity;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Mono;
import reactor.test.StepVerifier;
import com.inditex.pricing.adapter.in.web.mapper.PriceMapper;
import com.inditex.pricing.domain.model.Brand;
import com.inditex.pricing.domain.model.Price;
import com.inditex.pricing.adapter.in.web.model.PriceResponse;
import com.inditex.pricing.adapter.in.web.model.BrandResponse;

import java.time.LocalDateTime;
import java.time.format.DateTimeParseException;

import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.when;

class PriceControllerTest {

    @Mock
    private RetrievePriceUseCase retrievePriceUseCase;

    @Mock
    private PriceMapper mapper;

    @Mock
    private ServerWebExchange exchange;

    @InjectMocks
    private PriceController controller;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void retrievePrice_shouldReturn200_whenPriceExists() {
        
        String date = "2020-06-14T10:00";
        Long brandId = 1L;
        Long productId = 35455L;

        LocalDateTime parsedDate = LocalDateTime.parse(date);

        Price domainPrice = Price.builder()
                .brand(new Brand(brandId, "ZARA"))
                .productId(productId)
                .price(35.5)
                .priceList(2)
                .startDate(parsedDate)
                .endDate(LocalDateTime.parse("2020-12-31T23:59:59"))
                .build();

        BrandResponse brandResponse = new BrandResponse();
        brandResponse.setId(brandId);
        brandResponse.setName("ZARA");

        PriceResponse dto = new PriceResponse();
        dto.setProductId(productId);
        dto.setPrice(35.5);
        dto.setPriceList(2);
        dto.setStartDate(date);
        dto.setEndDate("2020-12-31T23:59:59");
        dto.setBrand(brandResponse);

        when(retrievePriceUseCase.retrievePrice(eq(parsedDate), eq(brandId), eq(productId)))
                .thenReturn(Mono.just(domainPrice));

        when(mapper.toDto(domainPrice)).thenReturn(dto);

        
        Mono<ResponseEntity<PriceResponse>> result = controller.retrievePrice(date, brandId, productId, exchange);

        
        StepVerifier.create(result)
                .expectNextMatches(response ->
                        response.getStatusCode().is2xxSuccessful()
                                && response.getBody() != null
                                && response.getBody().getProductId().equals(productId)
                                && response.getBody().getBrand().getName().equals("ZARA"))
                .verifyComplete();
    }

    @Test
    void retrievePrice_shouldReturn204_whenNoPriceFound() {
        
        String date = "2020-06-14T10:00";
        Long brandId = 1L;
        Long productId = 35455L;
        LocalDateTime parsedDate = LocalDateTime.parse(date);

        when(retrievePriceUseCase.retrievePrice(eq(parsedDate), eq(brandId), eq(productId)))
                .thenReturn(Mono.empty());

        
        Mono<ResponseEntity<PriceResponse>> result = controller.retrievePrice(date, brandId, productId, exchange);

        
        StepVerifier.create(result)
                .expectNextMatches(response -> response.getStatusCodeValue() == 204)
                .verifyComplete();
    }


}
