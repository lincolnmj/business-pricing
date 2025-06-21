package com.inditex.pricing.unit.domain.service;

import com.inditex.pricing.domain.model.Brand;
import com.inditex.pricing.domain.model.Price;
import com.inditex.pricing.domain.service.PriceSelectorService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

class PriceSelectorServiceTest {

    private PriceSelectorService service;
    private final Brand brand = new Brand(1L, "ZARA");

    @BeforeEach
    void setUp() {
        service = new PriceSelectorService();
    }

    @Test
    void shouldReturnPriceWithHighestPriority() {
        LocalDateTime now = LocalDateTime.now();

        Price lowPriority = new Price(brand, 1L, 1, 0, now.minusDays(1), now.plusDays(1), Double.valueOf("30.00"), "EUR");
        Price highPriority = new Price(brand, 1L, 2, 2, now.minusDays(1), now.plusDays(1), Double.valueOf("35.00"), "EUR");

        Optional<Price> result = service.selectApplicablePrice(List.of(lowPriority, highPriority));

        assertTrue(result.isPresent());
        assertEquals(highPriority, result.get());
    }

    @Test
    void shouldReturnEmptyWhenListIsEmpty() {
        Optional<Price> result = service.selectApplicablePrice(List.of());

        assertTrue(result.isEmpty());
    }

    @Test
    void shouldReturnOnlyElementWhenSinglePrice() {
        LocalDateTime now = LocalDateTime.now();
        Price only = new Price(brand, 1L, 1, 1, now.minusDays(1), now.plusDays(1), Double.valueOf("40.00"), "EUR");

        Optional<Price> result = service.selectApplicablePrice(List.of(only));

        assertTrue(result.isPresent());
        assertEquals(only, result.get());
    }
}