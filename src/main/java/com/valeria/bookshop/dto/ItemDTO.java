package com.valeria.bookshop.dto;

import lombok.Data;

import java.util.List;

@Data
public class ItemDTO {
    private Long id;
    private String name;
    private String image;
    private double price;
    private String publishYear;
    private List<BrandDTO> brands;
    private GroupDTO group;
    private List<CategoryDTO> categories;
}
