package com.inditex.pricing.infraestructure.adapter.out.h2.mapper;

import com.inditex.pricing.domain.model.Brand;
import com.inditex.pricing.domain.model.Price;
import com.inditex.pricing.infraestructure.adapter.out.h2.entity.PriceEntity;
import org.springframework.stereotype.Component;
@Component
public class PriceEntityMapper {
    public Price toDomain(PriceEntity entity) {
        final Brand brand = new Brand(entity.getBrandId(), entity.getName());
        return new Price(
                brand,
                entity.getProductId(),
                entity.getPriceList(),
                entity.getPriority(),
                entity.getStartDate(),
                entity.getEndDate(),
                entity.getPrice(),
                entity.getCurrency()
        );
    }
}
