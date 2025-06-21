package com.inditex.pricing.adapter.in.web.mapper;

import com.inditex.pricing.domain.model.Price;
import com.inditex.pricing.adapter.in.web.model.PriceResponse;
import org.springframework.stereotype.Component;

@Component
public class PriceMapper {
    public PriceResponse toDto(Price domain) {
        final PriceResponse dto = new PriceResponse();
        dto.setBrandId(domain.getBrand().getId());
        dto.setStartDate(domain.getStartDate().toString());
        dto.setEndDate(domain.getEndDate().toString());
        dto.setPriceList(domain.getPriceList());
        dto.setProductId(domain.getProductId());
        dto.setPrice(domain.getPrice());
        return dto;
    }
}
