package com.valeria.shop.service;

import com.valeria.shop.db.entity.CategoryEntity;
import com.valeria.shop.db.repository.CategoryRepository;
import com.valeria.shop.dto.CategoryDTO;
import com.valeria.shop.exception.BadRequestException;
import com.valeria.shop.exception.NotFoundException;
import com.valeria.shop.mapper.CategoryMapper;
import com.valeria.shop.request.CreateOrUpdateCategoryRequest;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Objects;

@Service
@Transactional
@RequiredArgsConstructor
public class CategoryService {
    private final CategoryRepository categoryRepository;
    private final CategoryMapper categoryMapper;

    public List<CategoryDTO> getAllCategories() {
        return categoryMapper.mapEntitiesToDTOs(categoryRepository.findAll());
    }

    public List<CategoryEntity> findEntitiesByIdsIn(Collection<Long> ids) {
        List<CategoryEntity> categoryEntities = categoryRepository.findAllById(ids);
        List<Long> notExistingIds = new ArrayList<>();
        categoryEntities.forEach(categoryEntity -> {
            if (!ids.contains(categoryEntity.getId())) {
                notExistingIds.add(categoryEntity.getId());
            }
        });
        if (!notExistingIds.isEmpty()) {
            throw new NotFoundException("Категории " + notExistingIds + "не найдены");
        }
        return categoryEntities;
    }

    public CategoryDTO addNewCategory(CreateOrUpdateCategoryRequest request) {
        categoryRepository.findCategoryByName(request.name()).ifPresent(categoryEntity -> {
            throw new BadRequestException("Категория " + request.name() + " уже существует");
        });
        CategoryEntity category = categoryMapper.mapToEntity(request);
        return categoryMapper.mapEntityToDto(categoryRepository.save(category));
    }

    public void deleteCategory(Long id) {
        boolean exists = categoryRepository.existsById(id);
        if (!exists) {
            throw new NotFoundException("Категория не найдена");
        }
        categoryRepository.deleteById(id);
    }

    public CategoryDTO updateCategory(Long id, CreateOrUpdateCategoryRequest request) {
        CategoryEntity categoryEntity = categoryRepository.findById(id).orElseThrow(
                () -> new NotFoundException("Категория не найдена")
        );
        if (!Objects.equals(categoryEntity.getName(), request.name())) {
            categoryRepository.findCategoryByName(request.name()).ifPresent(entity -> {
                throw new BadRequestException("Название категории " + request.name() + " уже используется, измените название");
            });
        }
        categoryEntity.setName(request.name());
        return categoryMapper.mapEntityToDto(categoryEntity);
    }
}
