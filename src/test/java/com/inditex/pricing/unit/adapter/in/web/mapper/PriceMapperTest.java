package com.inditex.pricing.unit.adapter.in.web.mapper;

import com.inditex.pricing.adapter.in.web.mapper.PriceMapper;
import com.inditex.pricing.adapter.in.web.model.PriceResponse;
import com.inditex.pricing.domain.model.Brand;
import com.inditex.pricing.domain.model.Price;
import org.junit.jupiter.api.Test;
import java.time.LocalDateTime;

import static org.junit.jupiter.api.Assertions.*;
public class PriceMapperTest {

    @Test
    void shouldMapDomainPriceToDtoCorrectly() {
        // Arrange
        Brand brand = new Brand(1L, "ZARA");
        LocalDateTime start = LocalDateTime.of(2023, 6, 1, 0, 0);
        LocalDateTime end = LocalDateTime.of(2023, 6, 30, 23, 59);
        Price domain = new Price(
                brand,
                35455L,
                1,
                1,
                start,
                end,
                Double.valueOf("25.99"),
                "EUR"
        );

        PriceMapper mapper = new PriceMapper();

        // Act
        PriceResponse dto = mapper.toDto(domain);

        // Assert
        assertNotNull(dto);
        assertEquals(brand.getId(), dto.getBrandId());
        assertEquals("2023-06-01T00:00", dto.getStartDate());
        assertEquals("2023-06-30T23:59", dto.getEndDate());
        assertEquals(domain.getPriceList(), dto.getPriceList());
        assertEquals(domain.getProductId(), dto.getProductId());
        assertEquals(domain.getPrice(), dto.getPrice());
    }
}
