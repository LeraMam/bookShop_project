package com.valeria.bookshop.dto;

import com.valeria.bookshop.db.entity.UserRole;
import lombok.Data;

@Data
public class UserDTO {
    private Long id;
    private String email;
    private String firebaseIdentityId;
    private UserRole role;
}
