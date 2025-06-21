package com.inditex.pricing.domain.model;

import java.time.LocalDateTime;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class Price {
    private Brand brand;
    private Long productId;
    private int priceList;
    private int priority;
    private LocalDateTime startDate;
    private LocalDateTime endDate;
    private Double price;
    private String currency;
}
