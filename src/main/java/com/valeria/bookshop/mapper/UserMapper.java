package com.valeria.bookshop.mapper;

import com.valeria.bookshop.db.entity.UserEntity;
import com.valeria.bookshop.dto.UserDTO;
import org.mapstruct.Mapper;
import org.mapstruct.MappingConstants;

import java.util.List;

@Mapper(componentModel = MappingConstants.ComponentModel.SPRING)
public interface UserMapper {
    List<UserDTO> mapToDTOList(List<UserEntity> entityList);
    UserDTO mapToDTO(UserEntity entity);
}
