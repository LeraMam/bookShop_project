package com.valeria.bookshop.db.entity;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@Table
public class UserEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(unique = true)
    private String firebaseIdentityId;
    @Enumerated(EnumType.STRING)
    private UserRole role;
    private String email;
    @OneToMany(cascade = CascadeType.ALL) //нужно прявязать заказ к пользователю, но так, чтобы при создании
    // пользователя не надо было создавать сразу и заказ. надо при создании заказа брать айди пользователя
    //и добавлять его в список заказов пользователя. это нужно делать когда будет реализована авторизация
    // и соответственно мы будем знать данные авторизованного пользователя
    private List<BucketEntity> buckets = new ArrayList<>();

}
