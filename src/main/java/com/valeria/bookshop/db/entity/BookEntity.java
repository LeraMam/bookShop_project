package com.valeria.bookshop.db.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@Table
public class BookEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String name;
    @Column(columnDefinition = "longtext")
    private String image;
    private double price;
    private String publishYear;

    @ManyToMany(fetch = FetchType.EAGER)
    private List<AuthorEntity> authors = new ArrayList<>();

    @ManyToOne(fetch = FetchType.EAGER)
    private PublisherEntity publisher;

    @ManyToMany(fetch = FetchType.EAGER)
    private List<CategoryEntity> categories = new ArrayList<>();
}
