package com.valeria.bookshop.db.repository;

import com.valeria.bookshop.db.entity.BookEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface BookRepository extends JpaRepository<BookEntity, Long> {
    @Query("select distinct b.publishYear from BookEntity b")
    List<Long> findYearsDistinct();

    @Query("select min(b.price) from BookEntity b")
    Long findMinPrice();

    @Query("select max(b.price) from BookEntity b")
    Long findMaxPrice();
}
