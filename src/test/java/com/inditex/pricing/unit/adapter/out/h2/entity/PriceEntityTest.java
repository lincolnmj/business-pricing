package com.inditex.pricing.unit.adapter.out.h2.entity;

import com.inditex.pricing.adapter.out.h2.entity.PriceEntity;
import org.junit.jupiter.api.Test;

import java.time.LocalDateTime;

import static org.assertj.core.api.Assertions.assertThat;

class PriceEntityTest {

    @Test
    void shouldBuildPriceEntityUsingBuilder() {
        // given
        LocalDateTime start = LocalDateTime.of(2020, 6, 14, 0, 0);
        LocalDateTime end = LocalDateTime.of(2020, 12, 31, 23, 59);

        // when
        PriceEntity price = PriceEntity.builder()
                .brandId(1L)
                .productId(35455L)
                .startDate(start)
                .endDate(end)
                .priceList(4)
                .priority(1)
                .price(38.95)
                .currency("EUR")
                .build();

        // then
        assertThat(price.getBrandId()).isEqualTo(1L);
        assertThat(price.getProductId()).isEqualTo(35455L);
        assertThat(price.getStartDate()).isEqualTo(start);
        assertThat(price.getEndDate()).isEqualTo(end);
        assertThat(price.getPriceList()).isEqualTo(4);
        assertThat(price.getPriority()).isEqualTo(1);
        assertThat(price.getPrice()).isEqualTo(38.95);
        assertThat(price.getCurrency()).isEqualTo("EUR");
    }

    @Test
    void shouldSetAndGetFields() {
        // given
        PriceEntity entity = new PriceEntity();

        // when
        entity.setBrandId(1L);
        entity.setProductId(35455L);
        entity.setStartDate(LocalDateTime.parse("2020-06-14T00:00"));
        entity.setEndDate(LocalDateTime.parse("2020-12-31T23:59"));
        entity.setPriceList(4);
        entity.setPriority(1);
        entity.setPrice(38.95);
        entity.setCurrency("EUR");

        // then
        assertThat(entity.getBrandId()).isEqualTo(1L);
        assertThat(entity.getProductId()).isEqualTo(35455L);
        assertThat(entity.getStartDate()).isEqualTo("2020-06-14T00:00");
        assertThat(entity.getEndDate()).isEqualTo("2020-12-31T23:59");
        assertThat(entity.getPriceList()).isEqualTo(4);
        assertThat(entity.getPriority()).isEqualTo(1);
        assertThat(entity.getPrice()).isEqualTo(38.95);
        assertThat(entity.getCurrency()).isEqualTo("EUR");
    }
}
