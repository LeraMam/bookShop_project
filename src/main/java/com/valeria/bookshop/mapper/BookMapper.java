package com.valeria.bookshop.mapper;

import com.valeria.bookshop.db.entity.BookEntity;
import com.valeria.bookshop.dto.BookDTO;
import com.valeria.bookshop.request.CreateOrUpdateBookRequest;
import com.valeria.bookshop.service.AuthorService;
import com.valeria.bookshop.service.CategoryService;
import com.valeria.bookshop.service.PublisherService;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingConstants;
import org.mapstruct.MappingTarget;
import org.springframework.beans.factory.annotation.Autowired;

@Mapper(componentModel = MappingConstants.ComponentModel.SPRING)
public abstract class BookMapper implements GenericCrudMapper<CreateOrUpdateBookRequest, BookEntity, BookDTO> {
    @Autowired
    protected CategoryService categoryService;
    @Autowired
    protected AuthorService authorService;
    @Autowired
    protected PublisherService publisherService;

    @Mapping(target = "publisher", expression = "java(publisherService.findEntityById(createOrUpdateBookRequest.publisher()))")
    @Mapping(target = "authors", expression = "java(authorService.findEntitiesByIdsIn(createOrUpdateBookRequest.authors()))")
    @Mapping(target = "categories", expression = "java(categoryService.findEntitiesByIdsIn(createOrUpdateBookRequest.categories()))")
    @Override
    public abstract BookEntity mapToEntity(CreateOrUpdateBookRequest createOrUpdateBookRequest);

    @Mapping(target = "publisher", expression = "java(publisherService.findEntityById(createOrUpdateBookRequest.publisher()))")
    @Mapping(target = "authors", expression = "java(authorService.findEntitiesByIdsIn(createOrUpdateBookRequest.authors()))")
    @Mapping(target = "categories", expression = "java(categoryService.findEntitiesByIdsIn(createOrUpdateBookRequest.categories()))")
    @Override
    public abstract BookEntity updateEntity(@MappingTarget BookEntity entity, CreateOrUpdateBookRequest createOrUpdateBookRequest);
}
