package com.valeria.bookshop.request;

import com.valeria.bookshop.db.entity.UserRole;

import java.util.ArrayList;
import java.util.List;

public record CreateOrUpdateUserRequest(String login, String password,
                                        UserRole ROLE, List<Long>orders) {
}
