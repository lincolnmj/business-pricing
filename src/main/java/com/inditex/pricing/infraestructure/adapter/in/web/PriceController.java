package com.inditex.pricing.infraestructure.adapter.in.web;

import com.inditex.pricing.application.port.in.ConsultPriceUseCase;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Mono;
import com.inditex.pricing.infraestructure.adapter.in.web.model.Price;

import java.time.LocalDateTime; 
@RestController
@RequiredArgsConstructor
public class PriceController implements PricesApi {

    private final ConsultPriceUseCase consultPriceService;


    @Override
    public Mono<ResponseEntity<Price>> getPrices(String date, Long brandId, Long productId, 
                                                ServerWebExchange exchange) {
        
        return consultPriceService.consultPrice(LocalDateTime.parse(date), brandId, productId)
                .map(this::toDto)
                .map(ResponseEntity::ok)
                .defaultIfEmpty(ResponseEntity.noContent().build());
    }

    @SuppressWarnings("PMD.UnusedPrivateMethod") 
    private Price toDto(com.inditex.pricing.domain.model.Price  domainPrice) {
        final Price price = new Price();
        price.setBrandId(domainPrice.getBrand().getId());
        price.setStartDate(String.valueOf(domainPrice.getStartDate()));
        price.setEndDate(String.valueOf(domainPrice.getEndDate()));
        price.setPriceList(domainPrice.getPriceList());
        price.setProductId(domainPrice.getProductId());
        price.setPrice(domainPrice.getPrice());
        return price;
    }
}
