package com.inditex.pricing.application.port.out;

import com.inditex.pricing.domain.model.Price;
import reactor.core.publisher.Mono;

import java.time.LocalDateTime; 

public interface PriceRetrievalPort {
    Mono<Price> retrievePriceToApply(LocalDateTime applicationDate, Long brandId, Long productId);

}