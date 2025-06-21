package com.inditex.pricing.adapter.out.h2.repository;

import com.inditex.pricing.adapter.out.h2.entity.PriceEntity;
import org.springframework.data.r2dbc.repository.Query;

import java.time.LocalDateTime; 

import org.springframework.data.repository.reactive.ReactiveCrudRepository;
import org.springframework.stereotype.Repository;
import org.springframework.data.repository.query.Param;
import reactor.core.publisher.Flux;

@Repository
public interface PriceR2dbcRepository extends ReactiveCrudRepository<PriceEntity, Long> {

    @Query("""
            SELECT * FROM PRICES
            WHERE PRODUCT_ID = :productId
              AND BRAND_ID = :brandId
              AND START_DATE <= :applicationDate
              AND END_DATE >= :applicationDate
              """)
    Flux<PriceEntity> findApplicablePrices(
            @Param("applicationDate") LocalDateTime applicationDate,
            @Param("brandId") Long brandId,
            @Param("productId") Long productId
    );
}
