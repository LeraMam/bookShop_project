package com.valeria.bookshop.mapper;

import com.valeria.bookshop.db.entity.AuthorEntity;
import com.valeria.bookshop.dto.AuthorDTO;
import com.valeria.bookshop.request.CreateOrUpdateAuthorRequest;
import org.mapstruct.Mapper;
import org.mapstruct.MappingConstants;

@Mapper(componentModel = MappingConstants.ComponentModel.SPRING)
public interface AuthorMapper extends GenericCrudMapper<CreateOrUpdateAuthorRequest, AuthorEntity, AuthorDTO>{
}
