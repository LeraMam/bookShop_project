package com.valeria.shop.dto;

import lombok.Data;

@Data
public class ItemInBucketDTO {
    private Long id;
    private String name;
    private String image;
    private double price;
    private int itemCount;
}
