package com.inditex.pricing.adapter.in.web.mapper;

import com.inditex.pricing.domain.model.Price;
import com.inditex.pricing.adapter.in.web.model.PriceResponse;
import com.inditex.pricing.adapter.in.web.model.BrandResponse;
import org.springframework.stereotype.Component;

@Component
public class PriceMapper {
    public PriceResponse toDto(Price domain) {
        final PriceResponse dto = new PriceResponse();

        final BrandResponse brandDto = new BrandResponse();
        brandDto.setId(domain.getBrand().getId());
        brandDto.setName(domain.getBrand().getName());

        dto.setBrand(brandDto);
        dto.setStartDate(domain.getStartDate().toString());
        dto.setEndDate(domain.getEndDate().toString());
        dto.setPriceList(domain.getPriceList());
        dto.setProductId(domain.getProductId());
        dto.setPrice(domain.getPrice());

        return dto;
    }
}
