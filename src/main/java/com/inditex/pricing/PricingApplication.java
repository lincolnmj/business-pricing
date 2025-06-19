package com.inditex.pricing;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.data.r2dbc.repository.config.EnableR2dbcRepositories;


@SpringBootApplication(scanBasePackages = "com.inditex.pricing")
@ComponentScan(basePackages = "com.inditex.pricing")
@EnableR2dbcRepositories(basePackages = "com.inditex.pricing.infraestructure.adapter.out.h2.repository")
public class PricingApplication {
    public static void main(String[] args) {
        SpringApplication.run(PricingApplication.class, args);
    }
}
