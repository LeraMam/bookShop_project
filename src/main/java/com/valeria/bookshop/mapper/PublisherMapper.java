package com.valeria.bookshop.mapper;

import com.valeria.bookshop.db.entity.PublisherEntity;
import com.valeria.bookshop.dto.PublisherDTO;
import com.valeria.bookshop.request.CreateOrUpdatePublisherRequest;
import org.mapstruct.Mapper;
import org.mapstruct.MappingConstants;

@Mapper(componentModel = MappingConstants.ComponentModel.SPRING)
public interface PublisherMapper extends GenericCrudMapper<CreateOrUpdatePublisherRequest, PublisherEntity, PublisherDTO>{
}
