package com.valeria.bookshop.db.repository;

import com.valeria.bookshop.db.entity.BookInBucketEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface BookInBucketRepository extends JpaRepository<BookInBucketEntity, Long> {

}
