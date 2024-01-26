package com.valeria.bookshop.controller;

import com.valeria.bookshop.dto.AuthorDTO;
import com.valeria.bookshop.request.CreateOrUpdateAuthorRequest;
import com.valeria.bookshop.service.AuthorService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/author")
public class AuthorController {
    private final AuthorService authorService;

    @Autowired
    public AuthorController(AuthorService authorService) {
        this.authorService = authorService;
    }

    @GetMapping()
    public List<AuthorDTO> getAllAuthors() {
        return authorService.getAllAuthors();
    }

    @PostMapping
    public AuthorDTO addNewAuthor(@RequestBody CreateOrUpdateAuthorRequest request) {
        return authorService.addNewAuthor(request);
    }

    @DeleteMapping(path = "{id}")
    public void deleteAuthor(@PathVariable Long id) {
        authorService.deleteAuthor(id);
    }

    @PutMapping(path = "{id}")
    public AuthorDTO updateAuthor(@PathVariable Long id, @RequestBody CreateOrUpdateAuthorRequest request) {
        return authorService.updateAuthor(id, request);
    }
}
