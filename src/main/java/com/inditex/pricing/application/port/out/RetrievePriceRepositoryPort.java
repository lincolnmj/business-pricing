package com.inditex.pricing.application.port.out;

import com.inditex.pricing.domain.model.Price;
import reactor.core.publisher.Flux;

import java.time.LocalDateTime; 

public interface RetrievePriceRepositoryPort {
    Flux<Price> findAllPrices(LocalDateTime applicationDate, Long brandId, Long productId);
}
