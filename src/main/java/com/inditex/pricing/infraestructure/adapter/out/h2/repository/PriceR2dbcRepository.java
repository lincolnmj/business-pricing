package com.inditex.pricing.infraestructure.adapter.out.h2.repository;

import com.inditex.pricing.infraestructure.adapter.out.h2.entity.PriceEntity;
import org.springframework.data.r2dbc.repository.Query;

import java.time.LocalDateTime; 

import org.springframework.data.repository.reactive.ReactiveCrudRepository;
import org.springframework.stereotype.Repository;
import org.springframework.data.repository.query.Param;
import reactor.core.publisher.Mono;

@Repository
public interface PriceR2dbcRepository extends ReactiveCrudRepository<PriceEntity, Long> {

    @Query("""
            SELECT
            p.PRODUCT_ID,p.BRAND_ID,p.START_DATE,p.END_DATE,
            p.PRIORITY, p.PRICE, p.CURRENCY, b.NAME
            FROM PRICES p
            JOIN
            BRAND b ON p.BRAND_ID = b.ID
            WHERE PRODUCT_ID = :productId
              AND BRAND_ID = :brandId
              AND START_DATE <= :applicationDate
              AND END_DATE >= :applicationDate
            ORDER BY PRIORITY DESC
            LIMIT 1
            """)
    Mono<PriceEntity> findApplicablePrice(
            @Param("applicationDate") LocalDateTime applicationDate,
            @Param("brandId") Long brandId,
            @Param("productId") Long productId
    );
}