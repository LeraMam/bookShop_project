package com.valeria.shop.service;

import com.valeria.shop.db.entity.ItemEntity;
import com.valeria.shop.db.repository.BrandRepository;
import com.valeria.shop.db.repository.ItemRepository;
import com.valeria.shop.db.repository.CategoryRepository;
import com.valeria.shop.db.repository.GroupRepository;
import com.valeria.shop.dto.ItemDTO;
import com.valeria.shop.exception.NotFoundException;
import com.valeria.shop.mapper.ItemMapper;
import com.valeria.shop.request.CreateOrUpdateItemRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ItemService {
    private final ItemRepository itemRepository;
    private final ItemMapper itemMapper;

    @Autowired
    public ItemService(ItemRepository itemRepository, BrandRepository brandRepository, GroupRepository groupRepository, CategoryRepository categoryRepository, ItemMapper itemMapper) {
        this.itemRepository = itemRepository;
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
            throw new NotFoundException("Товар с заданным id " + bookId + " не существует");
        }
        itemRepository.deleteById(bookId);
    }

    public ItemEntity findEntityById(Long id) {
        return itemRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("Товар с заданным id " + id + " не существует"));
    }
}
