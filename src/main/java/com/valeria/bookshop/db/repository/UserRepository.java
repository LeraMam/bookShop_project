package com.valeria.bookshop.db.repository;

import com.valeria.bookshop.db.entity.UserEntity;
import com.valeria.bookshop.db.entity.UserRole;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<UserEntity, Long> {
    Optional<UserEntity> findByFirebaseIdentityId(String login);
    List<UserEntity> findAllByRole(UserRole role);

    Optional<UserEntity> findByEmail(String email);
}
