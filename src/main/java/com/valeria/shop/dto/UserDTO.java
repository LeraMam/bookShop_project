package com.valeria.shop.dto;

import com.valeria.shop.db.entity.UserRole;
import lombok.Data;

@Data
public class UserDTO {
    private Long id;
    private String email;
    private String firebaseIdentityId;
    private UserRole role;
}
