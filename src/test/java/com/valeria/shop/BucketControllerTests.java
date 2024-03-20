package com.valeria.shop;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.valeria.shop.controller.BucketController;
import com.valeria.shop.db.entity.BucketState;
import com.valeria.shop.dto.BrandDTO;
import com.valeria.shop.dto.BucketDTO;
import com.valeria.shop.request.ChangeBucketStateRequest;
import com.valeria.shop.request.CreateOrUpdateItemRequest;
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

import static org.junit.jupiter.api.Assertions.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@RunWith(SpringRunner.class)
@SpringBootTest
@AutoConfigureMockMvc
@WithMockUser(roles = "ADMIN")
public class BucketControllerTests {
    @Autowired
    MockMvc mockMvc;
    @Autowired
    BucketController bucketController;
    @Test
    public void getAllBucketsForAnalyticsTest() throws Exception {
        MvcResult result = this.mockMvc.perform(get("/api/bucket/analyticsCategories"))
                .andDo(print())
                .andExpect(status().isOk())
                .andReturn();
        String responseContent = result.getResponse().getContentAsString();
        ObjectMapper objectMapper = new ObjectMapper();
        List<BucketDTO> bucketList = objectMapper.readValue(responseContent, new TypeReference<List<BucketDTO>>() {});
        assertNotNull(bucketList);
        assertFalse(bucketList.isEmpty());
    }
    @Test
    public void getAllByStateTest() throws Exception {
        MvcResult result = this.mockMvc.perform(get("/api/bucket/admin")
                .param("state", "IN_PROCESSING"))
                .andDo(print())
                .andExpect(status().isOk())
                .andReturn();
        String responseContent = result.getResponse().getContentAsString();
        ObjectMapper objectMapper = new ObjectMapper();
        List<BucketDTO> bucketList = objectMapper.readValue(responseContent, new TypeReference<List<BucketDTO>>() {});
        assertNotNull(bucketList);
        assertFalse(bucketList.isEmpty());
        for(BucketDTO element : bucketList){
            assertEquals(BucketState.IN_PROCESSING, element.getState());
        }
    }
    @Test
    public void changeBucketStateToProcessTest() throws Exception {
        long testBucketId = 3L;
        ChangeBucketStateRequest bucketStateRequest = new ChangeBucketStateRequest(BucketState.RESOLVED);
        String requestContent = new ObjectMapper().writeValueAsString(bucketStateRequest);

        MvcResult result = this.mockMvc.perform(put("/api/bucket/{id}", testBucketId)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(requestContent))
                .andDo(print())
                .andExpect(status().isOk())
                .andReturn();
    }
}
