package com.valeria.bookshop.mapper;

import org.mapstruct.MappingTarget;

import java.util.List;

public interface GenericCrudMapper<CrUp, Entity, Dto> {
    public abstract Entity mapToEntity(CrUp crUp);

    public abstract Entity updateEntity(@MappingTarget Entity entity, CrUp crUp);

    public abstract Dto mapEntityToDto(Entity entity);

    public abstract List<Dto> mapEntitiesToDTOs(List<Entity> entityList);
}
