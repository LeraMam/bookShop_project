package com.valeria.shop;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.valeria.shop.controller.ItemController;
import com.valeria.shop.dto.ItemDTO;
import com.valeria.shop.request.CreateOrUpdateItemRequest;
import com.valeria.shop.service.BrandService;
import com.valeria.shop.service.CategoryService;
import com.valeria.shop.service.GroupService;
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

import java.util.ArrayList;
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
public class ItemControllerTests {
    @Autowired
    MockMvc mockMvc;
    @Autowired
    ItemController itemController;
    @Autowired
    BrandService brandService;
    @Autowired
    GroupService groupService;
    @Autowired
    CategoryService categoryService;
    @Test
    public void getMethodFilterItemsControllerTest() throws Exception {
        MvcResult result = this.mockMvc.perform(get("/api/item")
                //не забыть накинуть еще пару параметров поиска, когда данные внесутся данные в бд
                .param("search", "т"))
                .andDo(print())
                .andExpect(status().isOk())
                .andReturn();
        String responseContent = result.getResponse().getContentAsString();
        ObjectMapper objectMapper = new ObjectMapper();
        List<ItemDTO> itemList = objectMapper.readValue(responseContent, new TypeReference<List<ItemDTO>>() {});
        assertNotNull(itemList);
        assertFalse(itemList.isEmpty());
    }
    @Test
    public void getMethodOneItemControllerTest() throws Exception {
        long testItemId = 3;
        MvcResult result = this.mockMvc.perform(get("/api/item/{id}", testItemId))
                .andDo(print())
                .andExpect(status().isOk())
                .andReturn();
        String responseContent = result.getResponse().getContentAsString();
        ObjectMapper objectMapper = new ObjectMapper();
        ItemDTO itemDTO = objectMapper.readValue(responseContent, new TypeReference<ItemDTO>() {});
        assertNotNull(itemDTO);
    }

    @Test
    public void postMethodItemControllerTest() throws Exception {
        List<Long> brands = new ArrayList<>();
        List<Long> categories = new ArrayList<>();
        Long group = 1L;
        categories.add(1L);
        brands.add(1L);
        CreateOrUpdateItemRequest itemRequest = new CreateOrUpdateItemRequest(
                "New item", "image", 12.9,
                "Беларусь", brands, group, categories);
        String requestContent = new ObjectMapper().writeValueAsString(itemRequest);
        MvcResult result = this.mockMvc.perform(post("/api/item")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(requestContent))
                .andDo(print())
                .andExpect(status().isOk())
                .andReturn();
    }

    @Test
    public void putMethodItemControllerTest() throws Exception {
        long testItemId = 3;
        List<Long> brands = new ArrayList<>();
        List<Long> categories = new ArrayList<>();
        Long group = 1L;
        categories.add(1L);
        brands.add(1L);
        CreateOrUpdateItemRequest itemRequest = new CreateOrUpdateItemRequest(
                "Change item", "image", 12.9,
                "Беларусь", brands, group, categories);
        String requestContent = new ObjectMapper().writeValueAsString(itemRequest);

        MvcResult result = this.mockMvc.perform(put("/api/item/{id}", testItemId)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(requestContent))
                .andDo(print())
                .andExpect(status().isOk())
                .andReturn();
    }
}
