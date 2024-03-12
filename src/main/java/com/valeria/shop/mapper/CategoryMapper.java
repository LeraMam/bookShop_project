package com.valeria.shop.mapper;

import com.valeria.shop.db.entity.CategoryEntity;
import com.valeria.shop.dto.CategoryDTO;
import com.valeria.shop.request.CreateOrUpdateCategoryRequest;
import org.mapstruct.Mapper;
import org.mapstruct.MappingConstants;

@Mapper(componentModel = MappingConstants.ComponentModel.SPRING)
public interface CategoryMapper extends GenericCrudMapper<CreateOrUpdateCategoryRequest, CategoryEntity, CategoryDTO> {
}
