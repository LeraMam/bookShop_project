package com.valeria.shop.request;

import com.valeria.shop.db.entity.UserRole;

import java.util.List;

public record CreateOrUpdateUserRequest(String login, String password,
                                        UserRole ROLE, List<Long>orders) {
}
