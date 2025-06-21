package com.inditex.pricing.unit.adapter.out.h2.entity;

import com.inditex.pricing.adapter.out.h2.entity.PriceEntity;
import org.junit.jupiter.api.Test;

import java.time.LocalDateTime;

import static org.junit.jupiter.api.Assertions.*;

class PriceEntityTest {

    @Test
    void testConstructor() {
         
        Long brandId = 1L;
        LocalDateTime startDate = LocalDateTime.now();
        LocalDateTime endDate = LocalDateTime.now().plusDays(1);
        int priceList = 1001;
        Long productId = 2002L;
        int priority = 1;
        Double price = Double.valueOf(99.99);
        String currency = "EUR";
        String name = "ZARA";

         
        PriceEntity priceEntity = new PriceEntity(
                brandId, startDate, endDate, priceList, productId, priority, price, currency, name
        );

         
        assertEquals(brandId, priceEntity.getBrandId());
        assertEquals(startDate, priceEntity.getStartDate());
        assertEquals(endDate, priceEntity.getEndDate());
        assertEquals(priceList, priceEntity.getPriceList());
        assertEquals(productId, priceEntity.getProductId());
        assertEquals(priority, priceEntity.getPriority());
        assertEquals(price, priceEntity.getPrice());
        assertEquals(currency, priceEntity.getCurrency());
        assertEquals(name, priceEntity.getName());
    }

    @Test
    void testBuilder() {
         
        Long brandId = 1L;
        LocalDateTime startDate = LocalDateTime.now();
        LocalDateTime endDate = LocalDateTime.now().plusDays(1);
        int priceList = 1001;
        Long productId = 2002L;
        int priority = 1;
        Double price = Double.valueOf(99.99);
        String currency = "EUR";
        String name = "Test Product";

         
        PriceEntity priceEntity = PriceEntity.builder()
                .brandId(brandId)
                .startDate(startDate)
                .endDate(endDate)
                .priceList(priceList)
                .productId(productId)
                .priority(priority)
                .price(price)
                .currency(currency)
                .name(name)
                .build();

         
        assertEquals(brandId, priceEntity.getBrandId());
        assertEquals(startDate, priceEntity.getStartDate());
        assertEquals(endDate, priceEntity.getEndDate());
        assertEquals(priceList, priceEntity.getPriceList());
        assertEquals(productId, priceEntity.getProductId());
        assertEquals(priority, priceEntity.getPriority());
        assertEquals(price, priceEntity.getPrice());
        assertEquals(currency, priceEntity.getCurrency());
        assertEquals(name, priceEntity.getName());
    }

    @Test
    void testSettersAndGetters() {
        PriceEntity entity = new PriceEntity();

        entity.setBrandId(1L);
        entity.setStartDate(LocalDateTime.now());
        entity.setEndDate(LocalDateTime.now().plusDays(1));
        entity.setPriceList(1);
        entity.setProductId(35455L);
        entity.setPriority(1);
        entity.setPrice(Double.valueOf(35.5));
        entity.setCurrency("EUR");
        entity.setName("Test");

        assertEquals(1L, entity.getBrandId());
        assertNotNull(entity.getStartDate());
        assertNotNull(entity.getEndDate());
        assertEquals(1, entity.getPriceList());
        assertEquals(35455L, entity.getProductId());
        assertEquals(1, entity.getPriority());
        assertEquals(Double.valueOf(35.5), entity.getPrice());
        assertEquals("EUR", entity.getCurrency());
        assertEquals("Test", entity.getName());
    }

    @Test
    void testEqualsHashCodeToString() {
        PriceEntity entity1 = new PriceEntity();
        entity1.setBrandId(1L);
        entity1.setProductId(35455L);
        entity1.setPriceList(1);

        PriceEntity entity2 = new PriceEntity();
        entity2.setBrandId(1L);
        entity2.setProductId(35455L);
        entity2.setPriceList(1);

        assertEquals(entity1, entity2);
        assertEquals(entity1.hashCode(), entity2.hashCode());
        assertTrue(entity1.toString().contains("PriceEntity"));
    }
 
}
