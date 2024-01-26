package com.valeria.bookshop.mapper;

import com.valeria.bookshop.db.entity.BucketEntity;
import com.valeria.bookshop.dto.BucketDTO;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingConstants;

import java.util.List;

@Mapper(componentModel = MappingConstants.ComponentModel.SPRING, uses = BookInBucketMapper.class)
public interface BucketMapper {
    @Mapping(target = "orderId", source = "id")
    BucketDTO mapToDTO(BucketEntity entity);

    List<BucketDTO> mapToDTOList(List<BucketEntity> bucketEntityList);
}
