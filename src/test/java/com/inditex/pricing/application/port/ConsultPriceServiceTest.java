package com.inditex.pricing.application.port;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.reactive.AutoConfigureWebTestClient;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.web.reactive.server.WebTestClient;

import lombok.extern.slf4j.Slf4j;


@Slf4j
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@AutoConfigureWebTestClient
class ConsultPriceControllerIntegrationTest {

    @Autowired
    private WebTestClient webTestClient;

    @Test
    void testPriceAt10AMOn14th() {
        webTestClient.get()
                .uri(uriBuilder -> uriBuilder
                        .path("/prices")
                        .queryParam("date", "2020-06-15T10:15:30")
                        .queryParam("productId", "35455")
                        .queryParam("brandId", "1")
                        .build())
                .exchange()
                .expectStatus().isOk()
                .expectBody()
                .jsonPath("$.price").isEqualTo(30.50);
    }

    @Test
    void testPriceAt4PMOn14th() {
        webTestClient.get()
                .uri(uriBuilder -> uriBuilder
                        .path("/prices")
                        .queryParam("date", "2020-06-14T16:00:00")
                        .queryParam("productId", "35455")
                        .queryParam("brandId", "1")
                        .build())
                .exchange()
                .expectStatus().isOk()
                .expectBody()
                .jsonPath("$.price").isEqualTo(25.45);
    }

    @Test
    void testPriceAt9PMOn14th() {
        webTestClient.get()
                .uri(uriBuilder -> uriBuilder
                        .path("/prices")
                        .queryParam("date", "2020-06-14T21:00:00")
                        .queryParam("productId", "35455")
                        .queryParam("brandId", "1")
                        .build())
                .exchange()
                .expectStatus().isOk()
                .expectBody()
                .jsonPath("$.price").isEqualTo(35.50);
    }

    @Test
    void testPriceAt10AMOn15th() {
        webTestClient.get()
                .uri(uriBuilder -> uriBuilder
                        .path("/prices")
                        .queryParam("date", "2020-06-15T10:00:00")
                        .queryParam("productId", "35455")
                        .queryParam("brandId", "1")
                        .build())
                .exchange()
                .expectStatus().isOk()
                .expectBody()
                .jsonPath("$.price").isEqualTo(30.50);
    }

    @Test
    void testPriceAt9PMOn16th() {
        webTestClient.get()
                .uri(uriBuilder -> uriBuilder
                        .path("/prices")
                        .queryParam("date", "2020-06-16T21:00:00")
                        .queryParam("productId", "35455")
                        .queryParam("brandId", "1")
                        .build())
                .exchange()
                .expectStatus().isOk()
                .expectBody()
                .jsonPath("$.price").isEqualTo(38.95);
    }




}