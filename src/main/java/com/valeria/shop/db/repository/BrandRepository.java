package com.valeria.shop.db.repository;

import com.valeria.shop.db.entity.BrandEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface BrandRepository extends JpaRepository<BrandEntity, Long> {
    Optional<BrandEntity> findBrandByName(String name);
}
