package com.inditex.pricing.adapter.out.h2.mapper;

import com.inditex.pricing.adapter.out.h2.entity.BrandEntity;
import com.inditex.pricing.domain.model.Brand;
import com.inditex.pricing.domain.model.Price;
import com.inditex.pricing.adapter.out.h2.entity.PriceEntity;
import org.springframework.stereotype.Component;
@Component
public class PricePersistenceMapper {
    public Price toDomain(PriceEntity priceEntity, BrandEntity brandEntity) {
        final Brand brand = Brand.builder()
                .id(brandEntity.getId())
                .name(brandEntity.getName())
                .build();

        return Price.builder()
                .brand(brand)
                .productId(priceEntity.getProductId())
                .priceList(priceEntity.getPriceList())
                .priority(priceEntity.getPriority())
                .startDate(priceEntity.getStartDate())
                .endDate(priceEntity.getEndDate())
                .price(priceEntity.getPrice())
                .currency(priceEntity.getCurrency())
                .build();
    }
}
