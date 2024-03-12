package com.valeria.shop.mapper;

import com.valeria.shop.db.entity.ItemEntity;
import com.valeria.shop.dto.ItemDTO;
import com.valeria.shop.request.CreateOrUpdateItemRequest;
import com.valeria.shop.service.BrandService;
import com.valeria.shop.service.CategoryService;
import com.valeria.shop.service.GroupService;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingConstants;
import org.mapstruct.MappingTarget;
import org.springframework.beans.factory.annotation.Autowired;

@Mapper(componentModel = MappingConstants.ComponentModel.SPRING)
public abstract class ItemMapper implements GenericCrudMapper<CreateOrUpdateItemRequest, ItemEntity, ItemDTO> {
    @Autowired
    protected CategoryService categoryService;
    @Autowired
    protected BrandService brandService;
    @Autowired
    protected GroupService groupService;

    @Mapping(target = "group", expression = "java(groupService.findEntityById(createOrUpdateItemRequest.group()))")
    @Mapping(target = "brands", expression = "java(brandService.findEntitiesByIdsIn(createOrUpdateItemRequest.brands()))")
    @Mapping(target = "categories", expression = "java(categoryService.findEntitiesByIdsIn(createOrUpdateItemRequest.categories()))")
    @Override
    public abstract ItemEntity mapToEntity(CreateOrUpdateItemRequest createOrUpdateItemRequest);

    @Mapping(target = "group", expression = "java(groupService.findEntityById(createOrUpdateItemRequest.group()))")
    @Mapping(target = "brands", expression = "java(brandService.findEntitiesByIdsIn(createOrUpdateItemRequest.brands()))")
    @Mapping(target = "categories", expression = "java(categoryService.findEntitiesByIdsIn(createOrUpdateItemRequest.categories()))")
    @Override
    public abstract ItemEntity updateEntity(@MappingTarget ItemEntity entity, CreateOrUpdateItemRequest createOrUpdateItemRequest);
}
