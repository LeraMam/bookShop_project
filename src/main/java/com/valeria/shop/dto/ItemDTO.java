package com.valeria.shop.dto;

import lombok.Data;

import java.util.List;

@Data
public class ItemDTO {
    private Long id;
    private String name;
    private String image;
    private double price;
    private String country;
    private List<BrandDTO> brands;
    private GroupDTO group;
    private List<CategoryDTO> categories;
}
