package com.inditex.pricing.adapter.out;

import com.inditex.pricing.adapter.out.h2.mapper.PricePersistenceMapper;
import com.inditex.pricing.adapter.out.h2.repository.PriceR2dbcRepository;
import com.inditex.pricing.domain.model.Price;
import com.inditex.pricing.application.port.out.RetrievePriceRepositoryPort;
import org.springframework.stereotype.Component;
import reactor.core.publisher.Flux;

import java.time.LocalDateTime; 

import lombok.RequiredArgsConstructor;

@Component
@RequiredArgsConstructor
public class H2PriceRepositoryAdapter implements RetrievePriceRepositoryPort {

    private final PriceR2dbcRepository priceRepository;
    private final PricePersistenceMapper mapper;

    @Override
    public Flux<Price> findAllPrices(LocalDateTime applicationDate, Long brandId, Long productId) {
        return priceRepository.findApplicablePrices(applicationDate, brandId, productId)
                .map(mapper::toDomain);
    }
}
