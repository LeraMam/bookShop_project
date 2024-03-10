package com.valeria.bookshop.controller;

import com.valeria.bookshop.dto.BrandDTO;
import com.valeria.bookshop.dto.ItemDTO;
import com.valeria.bookshop.dto.CategoryDTO;
import com.valeria.bookshop.request.CreateOrUpdateItemRequest;
import com.valeria.bookshop.service.ItemService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.util.CollectionUtils;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/item")
public class ItemController {
    private final ItemService itemService;

    @Autowired
    public ItemController(ItemService itemService) {
        this.itemService = itemService;
    }

    @GetMapping()
    public List<ItemDTO> getAllItems(
            @RequestParam(value = "search", required = false) String search,
            @RequestParam(value = "category", required = false) List<Long> category,
            @RequestParam(value = "brand", required = false) List<Long> authors,
            @RequestParam(value = "group", required = false) List<Long> publisher,
            @RequestParam(value = "country", required = false) List<String> years,
            @RequestParam(value = "priceMin", required = false) Double priceMin,
            @RequestParam(value = "priceMax", required = false) Double priceMax
    ) {
        return itemService.getAllItems()
                .stream()
                .filter(itemDTO -> search == null || itemDTO.getName().contains(search))
                .filter(itemDTO -> CollectionUtils.isEmpty(category) || CollectionUtils.containsAny(category, itemDTO.getCategories().stream().map(CategoryDTO::getId).toList()))
                .filter(itemDTO -> CollectionUtils.isEmpty(authors) || CollectionUtils.containsAny(authors, itemDTO.getBrands().stream().map(BrandDTO::getId).toList()))
                .filter(itemDTO -> CollectionUtils.isEmpty(publisher) || CollectionUtils.containsInstance(publisher, itemDTO.getGroup().getId()))
                .filter(itemDTO -> CollectionUtils.isEmpty(years) || years.contains(itemDTO.getCountry()))
                .filter(itemDTO -> priceMin == null || itemDTO.getPrice() >= priceMin)
                .filter(itemDTO -> priceMax == null || itemDTO.getPrice() <= priceMax)
                .collect(Collectors.toList());
    }

    @GetMapping("/{id}")
    public ItemDTO getOne(@PathVariable Long id) {
        return itemService.getOne(id);
    }

    @PreAuthorize("hasAuthority('ROLE_ADMIN')")
    @PostMapping()
    public ItemDTO addNewItem(@RequestBody CreateOrUpdateItemRequest request) {
        System.out.println(request);
        return itemService.addNewItem(request);
    }

    @PreAuthorize("hasAuthority('ROLE_ADMIN')")
    @DeleteMapping(path = "/{itemId}")
    public void deleteItem(@PathVariable("itemId") Long bookId) {
        itemService.deleteItem(bookId);
    }

    @PreAuthorize("hasAuthority('ROLE_ADMIN')")
    @PutMapping(path = "/{itemId}")
    public ItemDTO updateItem(@PathVariable Long itemId, @RequestBody CreateOrUpdateItemRequest request) {
        return itemService.updateItem(itemId, request);
    }
}
