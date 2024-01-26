package com.valeria.bookshop.mapper;

import com.valeria.bookshop.db.entity.BookInBucketEntity;
import com.valeria.bookshop.dto.BookInBucketDTO;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingConstants;

@Mapper(componentModel = MappingConstants.ComponentModel.SPRING)
public interface BookInBucketMapper {

    @Mapping(target = "id", source = "book.id")
    @Mapping(target = "name", source = "book.name")
    @Mapping(target = "image", source = "book.image")
    @Mapping(target = "price", source = "book.price")
    BookInBucketDTO mapToDTO(BookInBucketEntity entity);
}
