package com.inditex.pricing.unit.adapter.out.h2.mapper;

import com.inditex.pricing.adapter.out.h2.entity.BrandEntity;
import com.inditex.pricing.adapter.out.h2.entity.PriceEntity;
import com.inditex.pricing.adapter.out.h2.mapper.PricePersistenceMapper;
import com.inditex.pricing.domain.model.Brand;
import com.inditex.pricing.domain.model.Price;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.time.LocalDateTime;

import static org.assertj.core.api.Assertions.assertThat;

class PricePersistenceMapperTest {

    private PricePersistenceMapper mapper;

    @BeforeEach
    void setUp() {
        mapper = new PricePersistenceMapper();
    }

    @Test
    void toDomain_shouldMapAllFieldsCorrectly() {
        
        LocalDateTime start = LocalDateTime.of(2020, 6, 14, 10, 0);
        LocalDateTime end   = LocalDateTime.of(2020, 12, 31, 23, 59);

        PriceEntity priceEntity = PriceEntity.builder()
                .brandId(1L)
                .productId(35455L)
                .priceList(5)
                .priority(7)
                .startDate(start)
                .endDate(end)
                .price(42.42)
                .currency("EUR")
                .build();

        BrandEntity brandEntity = BrandEntity.builder()
                .id(1L)
                .name("ZARA")
                .build();

        
        Price domain = mapper.toDomain(priceEntity, brandEntity);

        
        Brand brand = domain.getBrand();
        assertThat(brand).isNotNull();
        assertThat(brand.getId()).isEqualTo(1L);
        assertThat(brand.getName()).isEqualTo("ZARA");

        // Verificamos campos directos de Price
        assertThat(domain.getProductId()).isEqualTo(35455L);
        assertThat(domain.getPriceList()).isEqualTo(5);
        assertThat(domain.getPriority()).isEqualTo(7);
        assertThat(domain.getStartDate()).isEqualTo(start);
        assertThat(domain.getEndDate()).isEqualTo(end);
        assertThat(domain.getPrice()).isEqualTo(42.42);
        assertThat(domain.getCurrency()).isEqualTo("EUR");
    }
}
