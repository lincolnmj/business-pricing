package com.inditex.pricing.infraestructure.adapter.out.h2.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor; 
import org.springframework.data.relational.core.mapping.Column;
import org.springframework.data.relational.core.mapping.Table;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.time.OffsetDateTime;
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Table("PRICES")
public class PriceEntity {

    @Column("BRAND_ID")
    private Long brandId;

    @Column("START_DATE")
    private LocalDateTime startDate;

    @Column("END_DATE")
    private LocalDateTime endDate;

    @Column("PRICE_LIST")
    private int priceList;

    @Column("PRODUCT_ID")
    private Long productId;

    @Column("PRIORITY")
    private int priority;

    @Column("PRICE")
    private BigDecimal price;

    @Column("CURRENCY")
    private String currency;

    @Column("NAME")
    private String name;
}