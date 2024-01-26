package com.valeria.bookshop.request;

import com.valeria.bookshop.db.entity.UserRole;

public record CreateUserRequest(String email, String password, UserRole role) {
}
