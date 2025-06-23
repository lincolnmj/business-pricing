package com.inditex.pricing.domain.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

@Getter
@AllArgsConstructor
@Builder
public class Brand {
    private Long id;
    private String name;
}
