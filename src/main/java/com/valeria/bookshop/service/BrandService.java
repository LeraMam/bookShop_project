package com.valeria.bookshop.service;

import com.valeria.bookshop.db.entity.BrandEntity;
import com.valeria.bookshop.db.repository.BrandRepository;
import com.valeria.bookshop.dto.BrandDTO;
import com.valeria.bookshop.exception.BadRequestException;
import com.valeria.bookshop.exception.NotFoundException;
import com.valeria.bookshop.mapper.BrandMapper;
import com.valeria.bookshop.request.CreateOrUpdateBrandRequest;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Objects;

@Service
public class BrandService {
    private final BrandRepository brandRepository;
    private final BrandMapper brandMapper;

    @Autowired
    public BrandService(BrandRepository brandRepository, BrandMapper brandMapper) {
        this.brandRepository = brandRepository;
        this.brandMapper = brandMapper;
    }

    public List<BrandDTO> getAllBrands() {
        return brandMapper.mapEntitiesToDTOs(brandRepository.findAll());
    }

    public List<BrandEntity> findEntitiesByIdsIn(Collection<Long> ids) {
        List<BrandEntity> brandEntities = brandRepository.findAllById(ids);
        List<Long> notExistingIds = new ArrayList<>();
        brandEntities.forEach(brandEntity -> {
            if (!ids.contains(brandEntity.getId())) {
                notExistingIds.add(brandEntity.getId());
            }
        });
        if (!notExistingIds.isEmpty()) {
            throw new NotFoundException("Бренды " + notExistingIds + "не найдены");
        }
        return brandEntities;
    }

    public BrandDTO addNewBrand(CreateOrUpdateBrandRequest request) {
        brandRepository.findBrandByName(request.name()).ifPresent(BrandEntity -> {
            throw new BadRequestException("Бренд " + request.name() + " уже существует");
        });
        BrandEntity brand = brandMapper.mapToEntity(request);
        return brandMapper.mapEntityToDto(brandRepository.save(brand));
    }

    public void deleteBrand(Long brandId) {
        boolean exists = brandRepository.existsById(brandId);
        if (!exists) {
            throw new NotFoundException("Бренд не найден");
        }
        brandRepository.deleteById(brandId);
    }

    @Transactional
    public BrandDTO updateBrand(Long brandId, CreateOrUpdateBrandRequest request) {
        BrandEntity brandEntity = brandRepository.findById(brandId).orElseThrow(
                () -> new NotFoundException("Бренд не найден")
        );
        if (!Objects.equals(brandEntity.getName(), request.name())) {
            brandRepository.findBrandByName(request.name()).ifPresent(entity -> {
                throw new BadRequestException("Название бренда " + request.name() + " уже используется, измените его");
            });
        }
        brandEntity.setName(request.name());
        return brandMapper.mapEntityToDto(brandEntity);
    }
}
