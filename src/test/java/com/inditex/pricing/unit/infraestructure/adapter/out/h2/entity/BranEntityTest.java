package com.inditex.pricing.unit.infraestructure.adapter.out.h2.entity;

import com.inditex.pricing.infraestructure.adapter.out.h2.entity.BrandEntity;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;
import java.time.LocalDateTime;

import static org.junit.jupiter.api.Assertions.*;

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

    @Test
    void testSettersAndGetters() {
        BrandEntity entity = new BrandEntity();
        entity.setId(1L);
        entity.setName("ZARA");

        assertEquals(1L, entity.getId());
        assertEquals("ZARA", entity.getName());
    }

    @Test
    void testEqualsHashCodeToString() {
        BrandEntity entity1 = new BrandEntity();
        entity1.setId(1L);
        entity1.setName("ZARA");

        BrandEntity entity2 = new BrandEntity();
        entity2.setId(1L);
        entity2.setName("ZARA");

        assertEquals(entity1, entity2);
        assertEquals(entity1.hashCode(), entity2.hashCode());
        assertTrue(entity1.toString().contains("BrandEntity"));
    }


}
