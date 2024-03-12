package com.valeria.shop.db.entity;

import org.springframework.security.core.GrantedAuthority;

public enum UserRole implements GrantedAuthority {
    ROLE_ADMIN, ROLE_USER, ROLE_ANONYMOUS;

    @Override
    public String getAuthority() {
        return name();
    }
}
