package com.inditex.pricing.domain.exception;

public class PriceNotFoundException extends RuntimeException {

    public PriceNotFoundException(Long productId, Long brandId, String applicationDate) {
        super(String.format("No se encontró una tarifa aplicable para productId=%d, brandId=%d, fecha=%s",
                productId, brandId, applicationDate));
    }
}