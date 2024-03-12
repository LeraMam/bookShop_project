package com.valeria.shop.mapper;

import com.valeria.shop.db.entity.UserEntity;
import com.valeria.shop.dto.UserDTO;
import org.mapstruct.Mapper;
import org.mapstruct.MappingConstants;

import java.util.List;

@Mapper(componentModel = MappingConstants.ComponentModel.SPRING)
public interface UserMapper {
    List<UserDTO> mapToDTOList(List<UserEntity> entityList);
    UserDTO mapToDTO(UserEntity entity);
}
