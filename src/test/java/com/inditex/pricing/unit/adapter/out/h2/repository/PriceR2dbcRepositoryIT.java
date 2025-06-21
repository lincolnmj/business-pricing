package com.inditex.pricing.unit.adapter.out.h2.repository;

import com.inditex.pricing.adapter.out.h2.entity.PriceEntity;
import com.inditex.pricing.adapter.out.h2.repository.PriceR2dbcRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.data.r2dbc.DataR2dbcTest;
import org.springframework.r2dbc.core.DatabaseClient;
import reactor.core.publisher.Flux;
import reactor.test.StepVerifier;
import java.time.LocalDateTime;

@DataR2dbcTest
class PriceR2dbcRepositoryIT {

    @Autowired
    private DatabaseClient databaseClient;

    @Autowired
    private PriceR2dbcRepository repository;

    private final LocalDateTime now = LocalDateTime.now();

    @BeforeEach
    void setUp() {
        // Crear tabla y datos de prueba
        databaseClient.sql("""
            CREATE TABLE IF NOT EXISTS PRICES (
              ID IDENTITY PRIMARY KEY,
              BRAND_ID BIGINT,
              PRODUCT_ID BIGINT,
              START_DATE TIMESTAMP,
              END_DATE TIMESTAMP,
              PRICE_LIST BIGINT,
              PRIORITY INT,
              PRICE DECIMAL(10,2),
              CURRENCY VARCHAR(3)
            );
            """).then().block();

        databaseClient.sql("DELETE FROM PRICES").then().block();

        databaseClient.sql("""
            INSERT INTO PRICES (BRAND_ID, PRODUCT_ID, START_DATE, END_DATE, PRICE_LIST, PRIORITY, PRICE, CURRENCY)
            VALUES (1, 35455, :start, :end, 1, 1, 35.50, 'EUR')
            """)
                .bind("start", now.minusDays(1))
                .bind("end", now.plusDays(1))
                .then()
                .block();
    }

    @Test
    void shouldFindApplicablePrices() {
        Flux<PriceEntity> result = repository.findApplicablePrices(now, 1L, 35455L);

        StepVerifier.create(result)
                .expectNextMatches(entity ->
                        entity.getBrandId().equals(1L) &&
                                entity.getProductId().equals(35455L) &&
                                entity.getPrice().compareTo(Double.valueOf("35.50")) == 0
                )
                .verifyComplete();
    }

    @Test
    void shouldReturnEmptyWhenNoMatch() {
        Flux<PriceEntity> result = repository.findApplicablePrices(now, 2L, 99999L);

        StepVerifier.create(result)
                .verifyComplete();
    }
}