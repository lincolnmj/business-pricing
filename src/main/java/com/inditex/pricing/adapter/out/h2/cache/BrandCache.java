package com.inditex.pricing.adapter.out.h2.cache;

import com.github.benmanes.caffeine.cache.AsyncCache;
import com.github.benmanes.caffeine.cache.Caffeine;
import com.inditex.pricing.adapter.out.h2.entity.BrandEntity;
import com.inditex.pricing.adapter.out.h2.repository.BrandR2dbcRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import reactor.core.publisher.Mono;

import java.time.Duration;
import java.util.concurrent.CompletableFuture;

@Component
@RequiredArgsConstructor
public class BrandCache {

    private final BrandR2dbcRepository brandRepository;

    private final AsyncCache<Long, BrandEntity> cache = Caffeine.newBuilder()
            .maximumSize(100)
            .expireAfterAccess(Duration.ofHours(12))
            .expireAfterWrite(Duration.ofDays(12))
            .buildAsync();

    public Mono<BrandEntity> getBrandById(Long brandId) {
        CompletableFuture<BrandEntity> future = cache.get(brandId, (key, executor) ->
                brandRepository.findById(key).toFuture()
        );
        return Mono.fromFuture(future);
    }
}