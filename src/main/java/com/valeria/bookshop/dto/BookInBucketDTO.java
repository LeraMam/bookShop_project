package com.valeria.bookshop.dto;

import lombok.Data;

@Data
public class BookInBucketDTO {
    private Long id;
    private String name;
    private String image;
    private double price;
    private int bookCount;
}
