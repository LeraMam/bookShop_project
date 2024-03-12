package com.valeria.shop.mapper;

import com.valeria.shop.db.entity.BucketEntity;
import com.valeria.shop.dto.BucketDTO;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingConstants;

import java.util.List;

@Mapper(componentModel = MappingConstants.ComponentModel.SPRING, uses = ItemInBucketMapper.class)
public interface BucketMapper {
    @Mapping(target = "orderId", source = "id")
    BucketDTO mapToDTO(BucketEntity entity);

    List<BucketDTO> mapToDTOList(List<BucketEntity> bucketEntityList);
}
