package com.valeria.bookshop.db.repository;

import com.valeria.bookshop.db.entity.ItemEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface ItemRepository extends JpaRepository<ItemEntity, Long> {
    @Query("select distinct b.publishYear from ItemEntity b")
    List<String> findYearsDistinct();

    @Query("select min(b.price) from ItemEntity b")
    Long findMinPrice();

    @Query("select max(b.price) from ItemEntity b")
    Long findMaxPrice();
}
