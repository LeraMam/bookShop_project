package com.valeria.shop;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.valeria.shop.controller.GroupController;
import com.valeria.shop.dto.CategoryDTO;
import com.valeria.shop.dto.GroupDTO;
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
public class GroupControllerTests {
    @Autowired
    MockMvc mockMvc;
    @Autowired
    GroupController groupController;
    @Test
    public void getMethodGroupControllerTest() throws Exception {
        MvcResult result = this.mockMvc.perform(get("/api/group"))
                .andDo(print())
                .andExpect(status().isOk())
                .andReturn();
        String responseContent = result.getResponse().getContentAsString();
        ObjectMapper objectMapper = new ObjectMapper();
        List<GroupDTO> categoryList = objectMapper.readValue(responseContent, new TypeReference<List<GroupDTO>>() {});
        assertNotNull(categoryList);
        assertFalse(categoryList.isEmpty());
    }
    @Test
    public void postMethodCategoryControllerTest() throws Exception {
        GroupDTO groupDTO = new GroupDTO();
        groupDTO.setName("New new group");
        String requestContent = new ObjectMapper().writeValueAsString(groupDTO);
        MvcResult result = this.mockMvc.perform(post("/api/group")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(requestContent))
                .andDo(print())
                .andExpect(status().isOk())
                .andReturn();
    }

    @Test
    public void putMethodCategoryControllerTest() throws Exception {
        long testGroupId = 1;
        GroupDTO groupDTO = new GroupDTO();
        groupDTO.setName("Changed new group");
        String requestContent = new ObjectMapper().writeValueAsString(groupDTO);

        MvcResult result = this.mockMvc.perform(put("/api/group/{id}", testGroupId)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(requestContent))
                .andDo(print())
                .andExpect(status().isOk())
                .andReturn();
    }
}
