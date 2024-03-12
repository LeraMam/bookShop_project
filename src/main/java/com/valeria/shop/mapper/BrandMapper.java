package com.valeria.shop.mapper;

import com.valeria.shop.db.entity.BrandEntity;
import com.valeria.shop.dto.BrandDTO;
import com.valeria.shop.request.CreateOrUpdateBrandRequest;
import org.mapstruct.Mapper;
import org.mapstruct.MappingConstants;

@Mapper(componentModel = MappingConstants.ComponentModel.SPRING)
public interface BrandMapper extends GenericCrudMapper<CreateOrUpdateBrandRequest, BrandEntity, BrandDTO>{
}
