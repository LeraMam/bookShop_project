package com.valeria.shop;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.UserRecord;
import com.valeria.shop.controller.UserController;
import com.valeria.shop.db.entity.UserEntity;
import com.valeria.shop.db.entity.UserRole;
import com.valeria.shop.db.repository.UserRepository;
import com.valeria.shop.dto.UserDTO;
import com.valeria.shop.request.CreateUserRequest;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@RunWith(SpringRunner.class)
@SpringBootTest
@AutoConfigureMockMvc
@WithMockUser(roles = "ADMIN")
public class UserControllerTests {
    @Autowired
    MockMvc mockMvc;
    @Autowired
    UserController userController;
    @Autowired
    private UserRepository userRepository;
    @Test
    public void getMethodUserControllerTest() throws Exception {
        MvcResult result = this.mockMvc.perform(get("/api/users"))
                .andDo(print())
                .andExpect(status().isOk())
                .andReturn();
        String responseContent = result.getResponse().getContentAsString();
        ObjectMapper objectMapper = new ObjectMapper();
        List<UserDTO> userList = objectMapper.readValue(responseContent, new TypeReference<List<UserDTO>>() {});
        assertNotNull(userList);
        assertFalse(userList.isEmpty());
    }
    @Test
    public void postMethodUserControllerTest() throws Exception {
        // Устанавливаем ожидаемые значения
        String email = "testingEmptyUser@gmail.com";
        String password = "password";
        UserRole role = UserRole.ROLE_USER;
        CreateUserRequest createUserRequest = new CreateUserRequest(email, password, role);

        MvcResult result = this.mockMvc.perform(post("/api/users/registration")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(new ObjectMapper().writeValueAsString(createUserRequest)))
                .andDo(print())
                .andExpect(status().isOk())
                .andReturn();

        String responseContent = result.getResponse().getContentAsString();
        UserDTO responseDTO = new ObjectMapper().readValue(responseContent, UserDTO.class);
        assertEquals(email, responseDTO.getEmail().toLowerCase());
        assertEquals(role, responseDTO.getRole());

        Optional<UserEntity> savedUserOptional = userRepository.findByEmail(email);
        assertTrue(((Optional<?>) savedUserOptional).isPresent());
        UserEntity savedUser = savedUserOptional.get();
        assertEquals(email, savedUser.getEmail().toLowerCase());
        assertEquals(role, savedUser.getRole());

        FirebaseAuth firebaseAuth = FirebaseAuth.getInstance();
        UserRecord userRecord = firebaseAuth.getUserByEmail(email);
        assertNotNull(userRecord);
        assertEquals(email, userRecord.getEmail().toLowerCase());
    }

}
