package com.inditex.pricing.unit.adapter.out.h2.entity;

import com.inditex.pricing.adapter.out.h2.entity.BrandEntity;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class BrandEntityTest {

    @Test
    void testAllArgsConstructorAndGetters() {
        BrandEntity brand = new BrandEntity(1L, "ZARA");
        assertEquals(1L, brand.getId());
        assertEquals("ZARA", brand.getName());
    }

    @Test
    void testNoArgsConstructorAndSetters() {
        BrandEntity brand = new BrandEntity();
        brand.setId(2L);
        brand.setName("ZARA");

        assertEquals(2L, brand.getId());
        assertEquals("ZARA", brand.getName());
    }

    @Test
    void testBuilder() {
        BrandEntity brand = BrandEntity.builder()
                .id(3L)
                .name("ZARA")
                .build();

        assertEquals(3L, brand.getId());
        assertEquals("ZARA", brand.getName());
    }

    @Test
    void testEqualsAndHashCode() {
        BrandEntity brand1 = new BrandEntity(1L, "ZARA");
        BrandEntity brand2 = new BrandEntity(1L, "ZARA");
        BrandEntity brand3 = new BrandEntity(2L, "OTHER");

        assertEquals(brand1, brand2);
        assertNotEquals(brand1, brand3);

        assertEquals(brand1.hashCode(), brand2.hashCode());
        assertNotEquals(brand1.hashCode(), brand3.hashCode());
    }

    @Test
    void testToString() {
        BrandEntity brand = new BrandEntity(1L, "ZARA");
        String toString = brand.toString();
        assertTrue(toString.contains("id=1"));
        assertTrue(toString.contains("name=ZARA"));
    }
}