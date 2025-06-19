package com.inditex.pricing.infraestructure.adapter.out.h2.entity;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

class BrandEntityTest {

    @Test
    void testConstructor() {
         
        Long id = 1L;
        String name = "ZARA";

         
        BrandEntity brandEntity = new BrandEntity(id, name);

         
        assertEquals(id, brandEntity.getId());
        assertEquals(name, brandEntity.getName());
    }

    @Test
    void testBuilder() {
         
        Long id = 1L;
        String name = "ZARA";

         
        BrandEntity brandEntity = BrandEntity.builder()
                .id(id)
                .name(name)
                .build();

         
        assertEquals(id, brandEntity.getId());
        assertEquals(name, brandEntity.getName());
    }
}
