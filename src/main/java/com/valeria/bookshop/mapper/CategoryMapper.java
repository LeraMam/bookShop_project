package com.valeria.bookshop.mapper;

import com.valeria.bookshop.db.entity.CategoryEntity;
import com.valeria.bookshop.dto.CategoryDTO;
import com.valeria.bookshop.request.CreateOrUpdateCategoryRequest;
import org.mapstruct.Mapper;
import org.mapstruct.MappingConstants;

@Mapper(componentModel = MappingConstants.ComponentModel.SPRING)
public interface CategoryMapper extends GenericCrudMapper<CreateOrUpdateCategoryRequest, CategoryEntity, CategoryDTO> {
}
