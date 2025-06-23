package com.inditex.pricing.unit.adapter.in.web.mapper;

import com.inditex.pricing.adapter.in.web.mapper.PriceMapper;
import com.inditex.pricing.domain.model.Brand;
import com.inditex.pricing.domain.model.Price;
import com.inditex.pricing.adapter.in.web.model.PriceResponse;
import com.inditex.pricing.adapter.in.web.model.BrandResponse;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.time.LocalDateTime;

import static org.assertj.core.api.Assertions.assertThat;

class PriceMapperTest {

    private PriceMapper mapper;

    @BeforeEach
    void setUp() {
        mapper = new PriceMapper();
    }

    @Test
    void toDto_shouldMapDomainToResponseCorrectly() {
        
        Brand brand = new Brand(1L, "ZARA");
        LocalDateTime start = LocalDateTime.of(2020, 6, 14, 10, 0);
        LocalDateTime end = LocalDateTime.of(2020, 12, 31, 23, 59, 59);

        Price domain = Price.builder()
                .brand(brand)
                .productId(35455L)
                .priceList(4)
                .startDate(start)
                .endDate(end)
                .price(38.95)
                .currency("EUR")
                .build();

        
        PriceResponse dto = mapper.toDto(domain);

        
        assertThat(dto).isNotNull();
        assertThat(dto.getProductId()).isEqualTo(35455L);
        assertThat(dto.getPriceList()).isEqualTo(4);
        assertThat(dto.getStartDate()).isEqualTo("2020-06-14T10:00");
        assertThat(dto.getEndDate()).isEqualTo("2020-12-31T23:59:59");
        assertThat(dto.getPrice()).isEqualTo(38.95);

        BrandResponse brandDto = dto.getBrand();
        assertThat(brandDto).isNotNull();
        assertThat(brandDto.getId()).isEqualTo(1L);
        assertThat(brandDto.getName()).isEqualTo("ZARA");
    }
}
