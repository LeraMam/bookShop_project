package com.valeria.bookshop.service;

import com.valeria.bookshop.db.entity.BookEntity;
import com.valeria.bookshop.db.repository.AuthorRepository;
import com.valeria.bookshop.db.repository.BookRepository;
import com.valeria.bookshop.db.repository.CategoryRepository;
import com.valeria.bookshop.db.repository.PublisherRepository;
import com.valeria.bookshop.dto.BookDTO;
import com.valeria.bookshop.exception.NotFoundException;
import com.valeria.bookshop.mapper.BookMapper;
import com.valeria.bookshop.request.CreateOrUpdateBookRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class BookService {
    private final BookRepository bookRepository;
    private final AuthorRepository authorRepository;
    private final PublisherRepository publisherRepository;
    private final CategoryRepository categoryRepository;
    private final BookMapper bookMapper;

    @Autowired
    public BookService(BookRepository bookRepository, AuthorRepository authorRepository, PublisherRepository publisherRepository, CategoryRepository categoryRepository, BookMapper bookMapper) {
        this.bookRepository = bookRepository;
        this.authorRepository = authorRepository;
        this.publisherRepository = publisherRepository;
        this.categoryRepository = categoryRepository;
        this.bookMapper = bookMapper;
    }

    public BookDTO getOne(Long id) {
        BookEntity bookEntity = findEntityById(id);
        return bookMapper.mapEntityToDto(bookEntity);
    }

    public List<BookDTO> getAllBooks() {
        return bookMapper.mapEntitiesToDTOs(bookRepository.findAll());
    }

    public BookDTO addNewBook(CreateOrUpdateBookRequest request) {
        BookEntity book = bookMapper.mapToEntity(request);
        return bookMapper.mapEntityToDto(bookRepository.save(book));
    }

    public BookDTO updateBook(Long id, CreateOrUpdateBookRequest request) {
        BookEntity bookEntity = findEntityById(id);
        bookEntity.getAuthors().clear();
        bookEntity.getCategories().clear();
        BookEntity book = bookMapper.updateEntity(bookEntity, request);
        return bookMapper.mapEntityToDto(bookRepository.save(book));
    }

    public void deleteBook(Long bookId) {
        boolean exists = bookRepository.existsById(bookId);
        if (!exists) {
            throw new NotFoundException("Товар с заданным id " + bookId + "не существует");
        }
        bookRepository.deleteById(bookId);
    }

    public BookEntity findEntityById(Long id) {
        return bookRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("Товар с заданным id " + id + "не существует"));
    }
}
