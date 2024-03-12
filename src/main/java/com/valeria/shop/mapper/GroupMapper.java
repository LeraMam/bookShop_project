package com.valeria.shop.mapper;

import com.valeria.shop.db.entity.GroupEntity;
import com.valeria.shop.dto.GroupDTO;
import com.valeria.shop.request.CreateOrUpdateGroupRequest;
import org.mapstruct.Mapper;
import org.mapstruct.MappingConstants;

@Mapper(componentModel = MappingConstants.ComponentModel.SPRING)
public interface GroupMapper extends GenericCrudMapper<CreateOrUpdateGroupRequest, GroupEntity, GroupDTO>{
}
