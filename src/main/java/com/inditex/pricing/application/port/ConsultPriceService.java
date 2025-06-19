package com.inditex.pricing.application.port;


import com.inditex.pricing.domain.exception.PriceNotFoundException;
import com.inditex.pricing.domain.model.Price;
import com.inditex.pricing.application.port.in.ConsultPriceUseCase;
import com.inditex.pricing.application.port.out.PriceRetrievalPort;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;

import java.time.LocalDateTime; 
@Service
@RequiredArgsConstructor
public class ConsultPriceService implements ConsultPriceUseCase {

    private final PriceRetrievalPort priceRetrievalPort;

    @Override
    public Mono<Price> consultPrice(LocalDateTime applicationDate, Long brandId, Long productId) {
        return priceRetrievalPort
                .retrievePriceToApply(applicationDate, brandId, productId)
                .switchIfEmpty(Mono.error(
                        new PriceNotFoundException(productId, brandId, applicationDate.toString())
                ));
    }
}
