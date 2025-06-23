package com.inditex.pricing.adapter.out.h2.repository;


import com.inditex.pricing.adapter.out.h2.entity.BrandEntity;
import org.springframework.data.repository.reactive.ReactiveCrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface BrandR2dbcRepository extends ReactiveCrudRepository<BrandEntity, Long> {

}
