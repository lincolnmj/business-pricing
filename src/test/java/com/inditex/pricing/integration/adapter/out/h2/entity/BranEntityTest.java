package com.inditex.pricing.integration.adapter.out.h2.entity;

import com.inditex.pricing.adapter.out.h2.entity.BrandEntity;
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
