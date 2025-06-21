package com.inditex.pricing.config;

import com.inditex.pricing.domain.service.PriceSelectorService;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class DomainConfig {
    @Bean
    public PriceSelectorService priceSelectorService() {
        return new PriceSelectorService();
    }
}
