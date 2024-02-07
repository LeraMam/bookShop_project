package com.valeria.bookshop.service;

import com.valeria.bookshop.db.entity.AuthorEntity;
import com.valeria.bookshop.db.repository.AuthorRepository;
import com.valeria.bookshop.dto.AuthorDTO;
import com.valeria.bookshop.exception.BadRequestException;
import com.valeria.bookshop.exception.NotFoundException;
import com.valeria.bookshop.mapper.AuthorMapper;
import com.valeria.bookshop.request.CreateOrUpdateAuthorRequest;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Objects;

@Service
public class AuthorService {
    private final AuthorRepository authorRepository;
    private final AuthorMapper authorMapper;

    @Autowired
    public AuthorService(AuthorRepository authorRepository, AuthorMapper authorMapper) {
        this.authorRepository = authorRepository;
        this.authorMapper = authorMapper;
    }

    public List<AuthorDTO> getAllAuthors() {
        return authorMapper.mapEntitiesToDTOs(authorRepository.findAll());
    }

    public List<AuthorEntity> findEntitiesByIdsIn(Collection<Long> ids) {
        List<AuthorEntity> authorEntities = authorRepository.findAllById(ids);
        List<Long> notExistingIds = new ArrayList<>();
        authorEntities.forEach(authorEntity -> {
            if (!ids.contains(authorEntity.getId())) {
                notExistingIds.add(authorEntity.getId());
            }
        });
        if (!notExistingIds.isEmpty()) {
            throw new NotFoundException("Авторы " + notExistingIds + "не найдены");
        }
        return authorEntities;
    }

    public AuthorDTO addNewAuthor(CreateOrUpdateAuthorRequest request) {
        authorRepository.findAuthorByName(request.name()).ifPresent(AuthorEntity -> {
            throw new BadRequestException("Автор " + request.name() + " уже существует");
        });
        AuthorEntity author = authorMapper.mapToEntity(request);
        return authorMapper.mapEntityToDto(authorRepository.save(author));
    }

    public void deleteAuthor(Long authorId) {
        boolean exists = authorRepository.existsById(authorId);
        if (!exists) {
            throw new NotFoundException("Автор не найден");
        }
        authorRepository.deleteById(authorId);
    }

    @Transactional
    public AuthorDTO updateAuthor(Long authorId, CreateOrUpdateAuthorRequest request) {
        AuthorEntity authorEntity = authorRepository.findById(authorId).orElseThrow(
                () -> new NotFoundException("Автор не найден")
        );
        if (!Objects.equals(authorEntity.getName(), request.name())) {
            authorRepository.findAuthorByName(request.name()).ifPresent(entity -> {
                throw new BadRequestException("Имя автора " + request.name() + " уже используется, измените имя");
            });
        }
        authorEntity.setName(request.name());
        return authorMapper.mapEntityToDto(authorEntity);
    }
}
