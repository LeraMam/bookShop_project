package com.valeria.bookshop.service;

import com.valeria.bookshop.db.entity.CategoryEntity;
import com.valeria.bookshop.db.repository.CategoryRepository;
import com.valeria.bookshop.dto.CategoryDTO;
import com.valeria.bookshop.exception.BadRequestException;
import com.valeria.bookshop.exception.NotFoundException;
import com.valeria.bookshop.mapper.CategoryMapper;
import com.valeria.bookshop.request.CreateOrUpdateCategoryRequest;
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
            throw new NotFoundException("Authors" + notExistingIds + "not exist");
        }
        return categoryEntities;
    }

    public CategoryDTO addNewCategory(CreateOrUpdateCategoryRequest request) {
        categoryRepository.findCategoryByName(request.name()).ifPresent(categoryEntity -> {
            throw new BadRequestException("Category " + request.name() + " already exists");
        });
        CategoryEntity category = categoryMapper.mapToEntity(request);
        return categoryMapper.mapEntityToDto(categoryRepository.save(category));
    }

    public void deleteCategory(Long id) {
        boolean exists = categoryRepository.existsById(id);
        if (!exists) {
            throw new NotFoundException("Category not exist");
        }
        categoryRepository.deleteById(id);
    }

    public CategoryDTO updateCategory(Long id, CreateOrUpdateCategoryRequest request) {
        CategoryEntity categoryEntity = categoryRepository.findById(id).orElseThrow(
                () -> new NotFoundException("Category not exist")
        );
        if (!Objects.equals(categoryEntity.getName(), request.name())) {
            categoryRepository.findCategoryByName(request.name()).ifPresent(entity -> {
                throw new BadRequestException("Category name " + request.name() + " is using, you can't use it");
            });
        }
        categoryEntity.setName(request.name());
        return categoryMapper.mapEntityToDto(categoryEntity);
    }
}
