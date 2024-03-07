package com.valeria.bookshop.controller;

import com.valeria.bookshop.db.repository.BookRepository;
import com.valeria.bookshop.service.AuthorService;
import com.valeria.bookshop.service.CategoryService;
import com.valeria.bookshop.service.PublisherService;
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
    private final AuthorService authorService;
    private final PublisherService publisherService;
    private final BookRepository bookRepository;

    @GetMapping()
    Map<String, Collection<?>> getAll() {
        return Map.of("categories", categoryService.getAllCategories(),
                "authors", authorService.getAllAuthors(),
                "publishers", publisherService.getAllPublishers());
    }

    @GetMapping("/years")
    Collection<String> getYears() {return bookRepository.findYearsDistinct();}

    @GetMapping("/price")
    Map<String, Long> getPriceRange() {
        Map<String, Long> map = new HashMap<>();
        map.put("max", bookRepository.findMaxPrice());
        map.put("min", bookRepository.findMinPrice());
        return map;
    }
}
