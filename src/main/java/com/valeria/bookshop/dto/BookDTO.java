package com.valeria.bookshop.dto;

import lombok.Data;

import java.util.List;

@Data
public class BookDTO {
    private Long id;
    private String name;
    private String image;
    private double price;
    private Integer publishYear;
    private List<AuthorDTO> authors;
    private PublisherDTO publisher;
    private List<CategoryDTO> categories;
}
