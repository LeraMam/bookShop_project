package com.valeria.shop;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.valeria.shop.controller.CategoryController;
import com.valeria.shop.dto.BrandDTO;
import com.valeria.shop.dto.CategoryDTO;
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

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@RunWith(SpringRunner.class)
@SpringBootTest
@AutoConfigureMockMvc
@WithMockUser(roles = "ADMIN")
public class CategoryControllerTests {
    @Autowired
    MockMvc mockMvc;
    @Autowired
    CategoryController categoryController;
    @Test
    public void getMethodCategoryControllerTest() throws Exception {
        MvcResult result = this.mockMvc.perform(get("/api/category"))
                .andDo(print())
                .andExpect(status().isOk())
                .andReturn();
        String responseContent = result.getResponse().getContentAsString();
        ObjectMapper objectMapper = new ObjectMapper();
        List<CategoryDTO> categoryList = objectMapper.readValue(responseContent, new TypeReference<List<CategoryDTO>>() {});
        assertNotNull(categoryList);
        assertFalse(categoryList.isEmpty());
    }
    @Test
    public void postMethodCategoryControllerTest() throws Exception {
        CategoryDTO categoryDTO = new CategoryDTO();
        categoryDTO.setName("New category");
        String requestContent = new ObjectMapper().writeValueAsString(categoryDTO);
        MvcResult result = this.mockMvc.perform(post("/api/category")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(requestContent))
                .andDo(print())
                .andExpect(status().isOk())
                .andReturn();
    }

    @Test
    public void putMethodCategoryControllerTest() throws Exception {
        long testCategoryId = 1;
        CategoryDTO categoryDTO = new CategoryDTO();
        categoryDTO.setName("Changed category");
        String requestContent = new ObjectMapper().writeValueAsString(categoryDTO);

        MvcResult result = this.mockMvc.perform(put("/api/category/{id}", testCategoryId)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(requestContent))
                .andDo(print())
                .andExpect(status().isOk())
                .andReturn();
    }
}
