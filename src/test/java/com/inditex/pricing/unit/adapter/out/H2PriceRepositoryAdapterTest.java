package com.inditex.pricing.unit.adapter.out;

import com.inditex.pricing.adapter.out.H2PriceRepositoryAdapter;
import com.inditex.pricing.adapter.out.h2.entity.PriceEntity;
import com.inditex.pricing.adapter.out.h2.mapper.PricePersistenceMapper;
import com.inditex.pricing.adapter.out.h2.repository.PriceR2dbcRepository;
import com.inditex.pricing.domain.model.Brand;
import com.inditex.pricing.domain.model.Price;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import reactor.core.publisher.Flux;
import reactor.test.StepVerifier;
import java.time.LocalDateTime;

import static org.mockito.Mockito.*;

class H2PriceRepositoryAdapterTest {

    private PriceR2dbcRepository priceRepository;
    private PricePersistenceMapper mapper;
    private H2PriceRepositoryAdapter adapter;

    private final LocalDateTime applicationDate = LocalDateTime.now();
    private final Long brandId = 1L;
    private final Long productId = 35455L;

    @BeforeEach
    void setUp() {
        priceRepository = mock(PriceR2dbcRepository.class);
        mapper = mock(PricePersistenceMapper.class);
        adapter = new H2PriceRepositoryAdapter(priceRepository, mapper);
    }

    @Test
    void shouldReturnMappedPricesFromRepository() {
        // Arrange
        PriceEntity entity = new PriceEntity();
        entity.setBrandId(brandId);
        entity.setProductId(productId);
        entity.setPriceList(1);
        entity.setPriority(1);
        entity.setStartDate(applicationDate.minusDays(1));
        entity.setEndDate(applicationDate.plusDays(1));
        entity.setPrice(Double.valueOf("29.99"));
        entity.setCurrency("EUR");
        entity.setName("ZARA");

        Price domain = new Price(
                new Brand(brandId, "ZARA"),
                productId,
                1,
                1,
                entity.getStartDate(),
                entity.getEndDate(),
                entity.getPrice(),
                entity.getCurrency()
        );

        when(priceRepository.findApplicablePrices(applicationDate, brandId, productId))
                .thenReturn(Flux.just(entity));
        when(mapper.toDomain(entity)).thenReturn(domain);

        // Act & Assert
        StepVerifier.create(adapter.findAllPrices(applicationDate, brandId, productId))
                .expectNext(domain)
                .verifyComplete();

        verify(priceRepository).findApplicablePrices(applicationDate, brandId, productId);
        verify(mapper).toDomain(entity);
    }
}