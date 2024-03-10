package com.valeria.bookshop.mapper;

import com.valeria.bookshop.db.entity.GroupEntity;
import com.valeria.bookshop.dto.GroupDTO;
import com.valeria.bookshop.request.CreateOrUpdateGroupRequest;
import org.mapstruct.Mapper;
import org.mapstruct.MappingConstants;

@Mapper(componentModel = MappingConstants.ComponentModel.SPRING)
public interface GroupMapper extends GenericCrudMapper<CreateOrUpdateGroupRequest, GroupEntity, GroupDTO>{
}
