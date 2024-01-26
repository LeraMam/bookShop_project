package com.valeria.bookshop.db.repository;

import com.valeria.bookshop.db.entity.BucketEntity;
import com.valeria.bookshop.db.entity.BucketState;
import com.valeria.bookshop.db.entity.UserEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface BucketRepository extends JpaRepository<BucketEntity, Long> {
    List<BucketEntity> findAllByState(BucketState state);

    Optional<BucketEntity> findByUserEntityAndState(UserEntity userEntity, BucketState bucketState);

    List<BucketEntity> findAllByUserEntity(UserEntity userEntity);
}
