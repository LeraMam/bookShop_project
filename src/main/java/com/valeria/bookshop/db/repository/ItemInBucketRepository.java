package com.valeria.bookshop.db.repository;

import com.valeria.bookshop.db.entity.ItemInBucketEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ItemInBucketRepository extends JpaRepository<ItemInBucketEntity, Long> {

}
