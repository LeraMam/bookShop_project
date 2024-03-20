package com.valeria.shop;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.valeria.shop.controller.BrandController;
import com.valeria.shop.dto.BrandDTO;
import org.junit.jupiter.api.Test;
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
class BrandControllerTests {
    @Autowired
    MockMvc mockMvc;
    @Autowired
    BrandController brandController;
    @Test
    public void getMethodBrandControllerTest() throws Exception {
        MvcResult result = this.mockMvc.perform(get("/api/brand"))
                .andDo(print())
                .andExpect(status().isOk())
                .andReturn();
        String responseContent = result.getResponse().getContentAsString();
        ObjectMapper objectMapper = new ObjectMapper();
        List<BrandDTO> brandList = objectMapper.readValue(responseContent, new TypeReference<List<BrandDTO>>() {});
        assertNotNull(brandList);
        assertFalse(brandList.isEmpty());
    }

    @Test
    public void postMethodBrandControllerTest() throws Exception {
        BrandDTO brandDTO = new BrandDTO();
        brandDTO.setName("New brand");
        String requestContent = new ObjectMapper().writeValueAsString(brandDTO);
        MvcResult result = this.mockMvc.perform(post("/api/brand")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(requestContent))
                .andDo(print())
                .andExpect(status().isOk())
                .andReturn();
    }

    @Test
    public void putMethodBrandControllerTest() throws Exception {
        long testBrandId = 1;
        BrandDTO brandPutDTO = new BrandDTO();
        brandPutDTO.setName("Changed brand");
        String requestContent = new ObjectMapper().writeValueAsString(brandPutDTO);

        MvcResult result = this.mockMvc.perform(put("/api/brand/{id}", testBrandId)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(requestContent))
                .andDo(print())
                .andExpect(status().isOk())
                .andReturn();
    }
}
