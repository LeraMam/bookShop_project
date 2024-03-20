package com.valeria.shop;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.valeria.shop.controller.MetaController;
import com.valeria.shop.db.repository.ItemRepository;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;

import java.util.Collection;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@RunWith(SpringRunner.class)
@SpringBootTest
@AutoConfigureMockMvc
public class MetaControllerTests {
    @Autowired
    MockMvc mockMvc;
    @Autowired
    MetaController metaController;
    @Autowired
    ItemRepository itemRepository;
    @Test
    public void getMethodMetaControllerTest() throws Exception {
        MvcResult result = this.mockMvc.perform(get("/api/meta"))
                .andDo(print())
                .andExpect(status().isOk())
                .andReturn();
        String responseContent = result.getResponse().getContentAsString();
        ObjectMapper objectMapper = new ObjectMapper();
        Map<String, Object> metaMap = objectMapper.readValue(responseContent, new TypeReference<Map<String, Object>>() {});
        assertNotNull(metaMap);
        assertFalse(metaMap.isEmpty());

        assertTrue(metaMap.containsKey("categories"));
        assertTrue(metaMap.containsKey("brands"));
        assertTrue(metaMap.containsKey("groups"));

        Collection<?> categories = (Collection<?>) metaMap.get("categories");
        Collection<?> brands = (Collection<?>) metaMap.get("brands");
        Collection<?> groups = (Collection<?>) metaMap.get("groups");

        assertNotNull(categories);
        assertFalse(categories.isEmpty());
        assertNotNull(brands);
        assertFalse(brands.isEmpty());
        assertNotNull(groups);
        assertFalse(groups.isEmpty());
    }
    @Test
    public void getMethodMetaCountriesControllerTest() throws Exception {
        MvcResult result = this.mockMvc.perform(get("/api/meta/country"))
                .andDo(print())
                .andExpect(status().isOk())
                .andReturn();
        String responseContent = result.getResponse().getContentAsString();
        ObjectMapper objectMapper = new ObjectMapper();
        Collection<String> countryCollection = objectMapper.readValue(responseContent, new TypeReference<Collection<String>>() {});
        assertNotNull(countryCollection);
        assertFalse(countryCollection.isEmpty());
    }
    @Test
    public void getMethodMetaPriceControllerTest() throws Exception {
        MvcResult result = this.mockMvc.perform(get("/api/meta/price"))
                .andDo(print())
                .andExpect(status().isOk())
                .andReturn();
        String responseContent = result.getResponse().getContentAsString();
        ObjectMapper objectMapper = new ObjectMapper();
        Map<String, Long> priceMap = objectMapper.readValue(responseContent, new TypeReference<Map<String, Long>>() {});
        assertNotNull(priceMap);
        assertFalse(priceMap.isEmpty());
    }
}
