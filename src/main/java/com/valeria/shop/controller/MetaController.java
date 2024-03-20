package com.valeria.shop.controller;

import com.valeria.shop.db.repository.ItemRepository;
import com.valeria.shop.service.BrandService;
import com.valeria.shop.service.CategoryService;
import com.valeria.shop.service.GroupService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/api/meta")
@RequiredArgsConstructor
public class MetaController {
    private final CategoryService categoryService;
    private final BrandService brandService;
    private final GroupService groupService;
    private final ItemRepository itemRepository;

    @GetMapping()
    Map<String, Collection<?>> getAll() {
        return Map.of("categories", categoryService.getAllCategories(),
                "brands", brandService.getAllBrands(),
                "groups", groupService.getAllGroups());
    }

    @GetMapping("/country")
    Collection<String> getCountries() {return itemRepository.findYearsDistinct();}

    @GetMapping("/price")
    Map<String, Long> getPriceRange() {
        Map<String, Long> map = new HashMap<>();
        map.put("max", itemRepository.findMaxPrice());
        map.put("min", itemRepository.findMinPrice());
        return map;
    }
}
