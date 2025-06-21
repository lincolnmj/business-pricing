package com.inditex.pricing.adapter.in.web;

import com.inditex.pricing.adapter.in.web.mapper.PriceMapper;
import com.inditex.pricing.application.port.in.RetrievePriceUseCase;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Mono;
import com.inditex.pricing.adapter.in.web.model.PriceResponse;
import com.inditex.pricing.adapter.in.web.PricesApiSpec;

import java.time.LocalDateTime; 
@RestController
@RequiredArgsConstructor
public class PriceController implements PricesApiSpec {
    private final RetrievePriceUseCase retrievePriceUseCase;
    private final PriceMapper mapper;
    @Override
    public Mono<ResponseEntity<PriceResponse>> getPrices(String date, Long brandId, Long productId,
                                                         ServerWebExchange exchange) {
        final LocalDateTime applicationDate = LocalDateTime.parse(date);
        return retrievePriceUseCase
                .retrievePrice(applicationDate, brandId, productId)
                .map(mapper::toDto)
                .map(ResponseEntity::ok)
                .defaultIfEmpty(ResponseEntity.noContent().build());
    }

}
