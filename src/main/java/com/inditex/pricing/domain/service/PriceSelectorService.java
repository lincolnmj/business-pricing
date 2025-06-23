package com.inditex.pricing.domain.service;

import com.inditex.pricing.domain.model.Price;

import java.util.Comparator;
import java.util.List;
import java.util.Optional;

public class PriceSelectorService {

    public Optional<Price> selectApplicablePrice(List<Price> candidates) {
        return candidates.stream()
                .max(Comparator.comparingInt(Price::getPriority));
    }
}
