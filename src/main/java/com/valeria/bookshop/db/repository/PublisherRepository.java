package com.valeria.bookshop.db.repository;

import com.valeria.bookshop.db.entity.PublisherEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface PublisherRepository extends JpaRepository<PublisherEntity, Long> {
    Optional<PublisherEntity> findPublisherByName(String name);
}
