package com.inditex.pricing.infraestructure.adapter.out;

import com.inditex.pricing.domain.model.Price;
import com.inditex.pricing.application.port.out.PriceRetrievalPort;
import com.inditex.pricing.infraestructure.adapter.out.h2.repository.PriceR2dbcRepository;
import com.inditex.pricing.infraestructure.adapter.out.h2.mapper.PriceEntityMapper;
import org.springframework.stereotype.Component;
import reactor.core.publisher.Mono;

import java.time.LocalDateTime; 

import lombok.RequiredArgsConstructor;

@Component
@RequiredArgsConstructor
public class H2PriceRepositoryAdapter implements PriceRetrievalPort {

    private final PriceR2dbcRepository priceRepository;
    private final PriceEntityMapper mapper;

    @Override
    public Mono<Price> retrievePriceToApply(LocalDateTime applicationDate, Long brandId, Long productId) {
        return priceRepository.findApplicablePrice(applicationDate, brandId, productId)
                .map(mapper::toDomain);
    }
}
