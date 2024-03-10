package com.valeria.bookshop.mapper;

import com.valeria.bookshop.db.entity.BrandEntity;
import com.valeria.bookshop.dto.BrandDTO;
import com.valeria.bookshop.request.CreateOrUpdateBrandRequest;
import org.mapstruct.Mapper;
import org.mapstruct.MappingConstants;

@Mapper(componentModel = MappingConstants.ComponentModel.SPRING)
public interface BrandMapper extends GenericCrudMapper<CreateOrUpdateBrandRequest, BrandEntity, BrandDTO>{
}
