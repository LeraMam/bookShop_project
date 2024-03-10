package com.valeria.bookshop.service;

import com.valeria.bookshop.db.entity.ItemEntity;
import com.valeria.bookshop.db.repository.BrandRepository;
import com.valeria.bookshop.db.repository.ItemRepository;
import com.valeria.bookshop.db.repository.CategoryRepository;
import com.valeria.bookshop.db.repository.GroupRepository;
import com.valeria.bookshop.dto.ItemDTO;
import com.valeria.bookshop.exception.NotFoundException;
import com.valeria.bookshop.mapper.ItemMapper;
import com.valeria.bookshop.request.CreateOrUpdateItemRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ItemService {
    private final ItemRepository itemRepository;
    private final BrandRepository brandRepository;
    private final GroupRepository groupRepository;
    private final CategoryRepository categoryRepository;
    private final ItemMapper itemMapper;

    @Autowired
    public ItemService(ItemRepository itemRepository, BrandRepository brandRepository, GroupRepository groupRepository, CategoryRepository categoryRepository, ItemMapper itemMapper) {
        this.itemRepository = itemRepository;
        this.brandRepository = brandRepository;
        this.groupRepository = groupRepository;
        this.categoryRepository = categoryRepository;
        this.itemMapper = itemMapper;
    }

    public ItemDTO getOne(Long id) {
        ItemEntity itemEntity = findEntityById(id);
        return itemMapper.mapEntityToDto(itemEntity);
    }

    public List<ItemDTO> getAllItems() {
        return itemMapper.mapEntitiesToDTOs(itemRepository.findAll());
    }

    public ItemDTO addNewItem(CreateOrUpdateItemRequest request) {
        ItemEntity item = itemMapper.mapToEntity(request);
        return itemMapper.mapEntityToDto(itemRepository.save(item));
    }

    public ItemDTO updateItem(Long id, CreateOrUpdateItemRequest request) {
        ItemEntity itemEntity = findEntityById(id);
        itemEntity.getBrands().clear();
        itemEntity.getCategories().clear();
        ItemEntity item = itemMapper.updateEntity(itemEntity, request);
        return itemMapper.mapEntityToDto(itemRepository.save(item));
    }

    public void deleteItem(Long bookId) {
        boolean exists = itemRepository.existsById(bookId);
        if (!exists) {
            throw new NotFoundException("Товар с заданным id " + bookId + "не существует");
        }
        itemRepository.deleteById(bookId);
    }

    public ItemEntity findEntityById(Long id) {
        return itemRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("Товар с заданным id " + id + "не существует"));
    }
}
