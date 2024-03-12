package com.valeria.shop.request;

import com.valeria.shop.db.entity.UserRole;

public record CreateUserRequest(String email, String password, UserRole role) {
}
