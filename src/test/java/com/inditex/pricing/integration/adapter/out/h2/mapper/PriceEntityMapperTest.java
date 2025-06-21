package com.inditex.pricing.integration.adapter.out.h2.mapper;

import com.inditex.pricing.adapter.out.h2.mapper.PricePersistenceMapper;
import com.inditex.pricing.domain.model.Price;
import com.inditex.pricing.adapter.out.h2.entity.PriceEntity;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;
import java.time.LocalDateTime;

import static org.junit.jupiter.api.Assertions.assertEquals;

class PriceEntityMapperTest {

    private final PricePersistenceMapper mapper = new PricePersistenceMapper();

    @Test
    void testToDomain() {
         
        PriceEntity entity = PriceEntity.builder()
                .brandId(1L)
                .name("ZARA")
                .productId(100L)
                .priceList(200)
                .priority(1)
                .startDate(LocalDateTime.now())
                .endDate(LocalDateTime.now().plusDays(1))
                .price(Double.valueOf(99.99))
                .currency("EUR")
                .build();

         
        Price price = mapper.toDomain(entity);

         
        assertEquals(entity.getBrandId(), price.getBrand().getId());
        assertEquals(entity.getName(), price.getBrand().getName());
        assertEquals(entity.getProductId(), price.getProductId());
        assertEquals(entity.getPriceList(), price.getPriceList());
        assertEquals(entity.getPriority(), price.getPriority());
        assertEquals(entity.getStartDate(), price.getStartDate());
        assertEquals(entity.getEndDate(), price.getEndDate());
        assertEquals(entity.getPrice(), price.getPrice());
        assertEquals(entity.getCurrency(), price.getCurrency());
    }
}
