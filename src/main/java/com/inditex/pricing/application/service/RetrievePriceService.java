package com.inditex.pricing.application.service;


import com.inditex.pricing.domain.exception.PriceNotFoundException;
import com.inditex.pricing.domain.model.Price;
import com.inditex.pricing.application.port.in.RetrievePriceUseCase;
import com.inditex.pricing.application.port.out.RetrievePriceRepositoryPort;
import com.inditex.pricing.domain.service.PriceSelectorService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;

import java.time.LocalDateTime; 
@Service
@RequiredArgsConstructor
public class RetrievePriceService implements RetrievePriceUseCase {

    private final RetrievePriceRepositoryPort repositoryPort;
    private final PriceSelectorService priceSelector;

    @Override
    public Mono<Price> retrievePrice(LocalDateTime applicationDate, Long brandId, Long productId) {
        return repositoryPort
                .findAllPrices(applicationDate, brandId, productId)
                .collectList()
                .flatMap(candidates -> {
                    return priceSelector.selectApplicablePrice(candidates)
                            .map(Mono::just)
                            .orElseGet(() -> Mono.error(
                                    new PriceNotFoundException(productId, brandId, applicationDate.toString())
                            ));
                });
    }
}
