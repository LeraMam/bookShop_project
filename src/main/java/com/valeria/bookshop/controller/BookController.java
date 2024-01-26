package com.valeria.bookshop.controller;

import com.valeria.bookshop.dto.AuthorDTO;
import com.valeria.bookshop.dto.BookDTO;
import com.valeria.bookshop.dto.CategoryDTO;
import com.valeria.bookshop.request.CreateOrUpdateBookRequest;
import com.valeria.bookshop.service.BookService;
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
@RequestMapping("/api/book")
public class BookController {
    private final BookService bookService;

    @Autowired
    public BookController(BookService bookService) {
        this.bookService = bookService;
    }

    @GetMapping()
    public List<BookDTO> getAllBooks(
            @RequestParam(value = "category", required = false) List<Long> category,
            @RequestParam(value = "author", required = false) List<Long> authors,
            @RequestParam(value = "publisher", required = false) List<Long> publisher,
            @RequestParam(value = "year", required = false) List<Integer> years,
            @RequestParam(value = "priceMin", required = false) Double priceMin,
            @RequestParam(value = "priceMax", required = false) Double priceMax
    ) {
        return bookService.getAllBooks()
                .stream()
                .filter(bookDTO -> CollectionUtils.isEmpty(category) || CollectionUtils.containsAny(category, bookDTO.getCategories().stream().map(CategoryDTO::getId).toList()))
                .filter(bookDTO -> CollectionUtils.isEmpty(authors) || CollectionUtils.containsAny(authors, bookDTO.getAuthors().stream().map(AuthorDTO::getId).toList()))
                .filter(bookDTO -> CollectionUtils.isEmpty(publisher) || CollectionUtils.containsInstance(publisher, bookDTO.getPublisher().getId()))
                .filter(bookDTO -> CollectionUtils.isEmpty(years) || CollectionUtils.containsInstance(years, bookDTO.getPublishYear()))
                .filter(bookDTO -> priceMin == null || bookDTO.getPrice() >= priceMin)
                .filter(bookDTO -> priceMax == null || bookDTO.getPrice() <= priceMax)
                .collect(Collectors.toList());
    }

    @GetMapping("/{id}")
    public BookDTO getOne(@PathVariable Long id) {
        return bookService.getOne(id);
    }

    @PreAuthorize("hasAuthority('ROLE_ADMIN')")
    @PostMapping()
    public BookDTO addNewBook(@RequestBody CreateOrUpdateBookRequest request) {
        return bookService.addNewBook(request);
    }

    @PreAuthorize("hasAuthority('ROLE_ADMIN')")
    @DeleteMapping(path = "/{bookId}")
    public void deleteBook(@PathVariable("bookId") Long bookId) {
        bookService.deleteBook(bookId);
    }

    @PreAuthorize("hasAuthority('ROLE_ADMIN')")
    @PutMapping(path = "/{bookId}")
    public BookDTO updateA(@PathVariable Long bookId, @RequestBody CreateOrUpdateBookRequest request) {
        return bookService.updateBook(bookId, request);
    }
}
