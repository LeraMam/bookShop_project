package com.valeria.bookshop.db.repository;

import com.valeria.bookshop.db.entity.AuthorEntity;
import com.valeria.bookshop.db.entity.CategoryEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface AuthorRepository extends JpaRepository<AuthorEntity, Long> {
    Optional<AuthorEntity> findAuthorByName(String name);
}
