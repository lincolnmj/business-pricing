package com.inditex.pricing.integration.adapter.in.web;

import com.inditex.pricing.adapter.in.web.PriceController;
import com.inditex.pricing.adapter.in.web.handler.GlobalExceptionHandler;
import com.inditex.pricing.adapter.in.web.mapper.PriceMapper;
import com.inditex.pricing.application.port.in.RetrievePriceUseCase;
import com.inditex.pricing.domain.exception.PriceNotFoundException;
import com.inditex.pricing.domain.model.Brand;
import com.inditex.pricing.domain.model.Price;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.boot.test.autoconfigure.web.reactive.WebFluxTest;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Import;
import org.springframework.context.annotation.Configuration;
import org.springframework.test.web.reactive.server.WebTestClient;
import org.springframework.beans.factory.annotation.Autowired;
import reactor.core.publisher.Mono;

import java.time.LocalDateTime;
import java.util.Map;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.when;

@WebFluxTest(controllers = PriceController.class)
@Import({ GlobalExceptionHandler.class, PriceMapper.class, PriceControllerTest.TestConfig.class })
class PriceControllerTest {

    @Autowired
    private WebTestClient client;

    @Autowired
    private RetrievePriceUseCase retrievePriceUseCase;

    @Configuration
    static class TestConfig {
        @Bean
        RetrievePriceUseCase retrievePriceUseCase() {
            return Mockito.mock(RetrievePriceUseCase.class);
        }
    }

    @BeforeEach
    void setUp() {
        retrievePriceUseCase = Mockito.mock(RetrievePriceUseCase.class);
        PriceMapper mapper = new PriceMapper();

        PriceController controller = new PriceController(retrievePriceUseCase, mapper);

        client = WebTestClient.bindToController(controller)
                .controllerAdvice(new GlobalExceptionHandler())
                .build();

        this.client = WebTestClient.bindToController(controller)
                .controllerAdvice(new GlobalExceptionHandler())
                .build();

        // Preparamos los cinco escenarios
        Map<String, Price> fixtures = Map.of(
                "2020-06-14T10:00", Price.builder()
                        .brand(new Brand(1L, "ZARA"))
                        .productId(35455L)
                        .priceList(1)
                        .price(35.50)
                        .startDate(LocalDateTime.parse("2020-06-14T00:00"))
                        .endDate(LocalDateTime.parse("2020-12-31T23:59:59"))
                        .build(),
                "2020-06-14T16:00", Price.builder()
                        .brand(new Brand(1L, "ZARA"))
                        .productId(35455L)
                        .priceList(2)
                        .price(25.45)
                        .startDate(LocalDateTime.parse("2020-06-14T15:00"))
                        .endDate(LocalDateTime.parse("2020-06-14T18:30"))
                        .build(),
                "2020-06-14T21:00", Price.builder()
                        .brand(new Brand(1L, "ZARA"))
                        .productId(35455L)
                        .priceList(1)
                        .price(35.50)
                        .startDate(LocalDateTime.parse("2020-06-14T00:00"))
                        .endDate(LocalDateTime.parse("2020-12-31T23:59:59"))
                        .build(),
                "2020-06-15T10:00", Price.builder()
                        .brand(new Brand(1L, "ZARA"))
                        .productId(35455L)
                        .priceList(3)
                        .price(30.50)
                        .startDate(LocalDateTime.parse("2020-06-15T00:00"))
                        .endDate(LocalDateTime.parse("2020-06-15T11:00"))
                        .build(),
                "2020-06-16T21:00", Price.builder()
                        .brand(new Brand(1L, "ZARA"))
                        .productId(35455L)
                        .priceList(4)
                        .price(38.95)
                        .startDate(LocalDateTime.parse("2020-06-15T16:00"))
                        .endDate(LocalDateTime.parse("2020-12-31T23:59:59"))
                        .build()
        );

        // Stub para cada fecha
        fixtures.forEach((dt, price) ->
                when(retrievePriceUseCase.retrievePrice(
                        eq(LocalDateTime.parse(dt)), eq(1L), eq(35455L)))
                        .thenReturn(Mono.just(price))
        );
    }

    @Test
    void whenInvalidDate_thenReturns400WithErrorBody() {

        when(retrievePriceUseCase.retrievePrice(any(), any(), any()))
                .thenReturn(Mono.empty());

        client.get()
                .uri(uri -> uri.path("/prices")
                        .queryParam("date", "invalid-date")
                        .queryParam("brandId", "1")
                        .queryParam("productId", "35455")
                        .build())
                .exchange()
                .expectStatus().isBadRequest()
                .expectHeader().contentType("application/json")
                .expectBody()
                .jsonPath("$.status").isEqualTo(400)
                .jsonPath("$.timestamp").isNotEmpty();
    }

    @Test
    void whenPriceNotFound_thenReturns404WithErrorBody() {
        // Simulo excepción de dominio
        when(retrievePriceUseCase.retrievePrice(eq(LocalDateTime.parse("2020-06-14T10:00")), eq(1L), eq(35455L)))
                .thenReturn(Mono.error(new PriceNotFoundException(35455L, 1L, "2020-06-14T10:00")));

        client.get()
                .uri(uri -> uri.path("/prices")
                        .queryParam("date", "2020-06-14T10:00")
                        .queryParam("brandId", "1")
                        .queryParam("productId", "35455")
                        .build())
                .exchange()
                .expectStatus().isNoContent()
                .expectBody()
                .jsonPath("$.status").isEqualTo(204)
                .jsonPath("$.timestamp").isNotEmpty();
    }


    @Test
    void test1_at10June14() {
        client.get().uri(uri ->
                        uri.path("/prices")
                                .queryParam("date", "2020-06-14T10:00")
                                .queryParam("brandId", "1")
                                .queryParam("productId", "35455")
                                .build()
                )
                .exchange()
                .expectStatus().isOk()
                .expectBody()
                .jsonPath("$.priceList").isEqualTo(1)
                .jsonPath("$.price").isEqualTo(35.50);
    }

    @Test
    void test2_at16June14() {
        client.get().uri(uri ->
                        uri.path("/prices")
                                .queryParam("date", "2020-06-14T16:00")
                                .queryParam("brandId", "1")
                                .queryParam("productId", "35455")
                                .build()
                )
                .exchange()
                .expectStatus().isOk()
                .expectBody()
                .jsonPath("$.priceList").isEqualTo(2)
                .jsonPath("$.price").isEqualTo(25.45);
    }

    @Test
    void test3_at21June14() {
        client.get().uri(uri ->
                        uri.path("/prices")
                                .queryParam("date", "2020-06-14T21:00")
                                .queryParam("brandId", "1")
                                .queryParam("productId", "35455")
                                .build()
                )
                .exchange()
                .expectStatus().isOk()
                .expectBody()
                .jsonPath("$.priceList").isEqualTo(1)
                .jsonPath("$.price").isEqualTo(35.50);
    }

    @Test
    void test4_at10June15() {
        client.get().uri(uri ->
                        uri.path("/prices")
                                .queryParam("date", "2020-06-15T10:00")
                                .queryParam("brandId", "1")
                                .queryParam("productId", "35455")
                                .build()
                )
                .exchange()
                .expectStatus().isOk()
                .expectBody()
                .jsonPath("$.priceList").isEqualTo(3)
                .jsonPath("$.price").isEqualTo(30.50);
    }

    @Test
    void test5_at21June16() {
        client.get().uri(uri ->
                        uri.path("/prices")
                                .queryParam("date", "2020-06-16T21:00")
                                .queryParam("brandId", "1")
                                .queryParam("productId", "35455")
                                .build()
                )
                .exchange()
                .expectStatus().isOk()
                .expectBody()
                .jsonPath("$.priceList").isEqualTo(4)
                .jsonPath("$.price").isEqualTo(38.95);
    }


}