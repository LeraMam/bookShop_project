package com.valeria.bookshop.db.repository;

import com.valeria.bookshop.db.entity.GroupEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface GroupRepository extends JpaRepository<GroupEntity, Long> {
    Optional<GroupEntity> findGroupByName(String name);
}
