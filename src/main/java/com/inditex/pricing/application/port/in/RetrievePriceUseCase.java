package com.inditex.pricing.application.port.in;

import com.inditex.pricing.domain.model.Price;
import reactor.core.publisher.Mono;

import java.time.LocalDateTime; 
public interface RetrievePriceUseCase {
    Mono<Price> retrievePrice(LocalDateTime applicationDate, Long brandId, Long productId);
}
