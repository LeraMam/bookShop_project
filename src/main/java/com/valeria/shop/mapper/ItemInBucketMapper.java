package com.valeria.shop.mapper;

import com.valeria.shop.db.entity.ItemInBucketEntity;
import com.valeria.shop.dto.ItemInBucketDTO;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingConstants;

@Mapper(componentModel = MappingConstants.ComponentModel.SPRING)
public interface ItemInBucketMapper {

    @Mapping(target = "id", source = "item.id")
    @Mapping(target = "name", source = "item.name")
    @Mapping(target = "image", source = "item.image")
    @Mapping(target = "price", source = "item.price")
    ItemInBucketDTO mapToDTO(ItemInBucketEntity entity);
}
