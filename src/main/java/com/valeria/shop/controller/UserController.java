package com.valeria.shop.controller;

import com.google.firebase.auth.FirebaseAuthException;
import com.valeria.shop.dto.UserDTO;
import com.valeria.shop.request.CreateUserRequest;
import com.valeria.shop.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/users")
@RequiredArgsConstructor
public class UserController {
    private final UserService userService;

    @PreAuthorize("hasAuthority('ROLE_ADMIN')")
    @GetMapping("")
    public List<UserDTO> getAllUsers() {
        return userService.getAllUsers();
    }

    @PreAuthorize("hasAuthority('ROLE_ADMIN')")
    @PostMapping("/registration")
    public UserDTO registerNewUser(@RequestBody CreateUserRequest request) throws FirebaseAuthException {
        return userService.addNewUser(request);
    }
    @DeleteMapping(path="/{userId}")
    public void deleteUser(@PathVariable("userId") Long userId) throws FirebaseAuthException {
        userService.deleteUser(userId);
    }
}
