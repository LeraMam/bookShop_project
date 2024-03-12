package com.valeria.shop.service.bucket.action;

import com.valeria.shop.db.entity.ItemInBucketEntity;
import com.valeria.shop.db.entity.BucketEntity;
import com.valeria.shop.db.repository.BucketRepository;
import com.valeria.shop.exception.BadRequestException;
import com.valeria.shop.request.BucketActionRequest;
import com.valeria.shop.service.ItemService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.Objects;

@Component
@RequiredArgsConstructor
public class AppendBookBucketActionProcessor implements BucketActionProcessor {
    private final BucketRepository bucketRepository;
    private final ItemService itemService;

    @Override
    public BucketAction bucketAction() {
        return BucketAction.APPEND_ITEM;
    }

    @Override
    public void process(BucketEntity bucketEntity, BucketActionRequest request) {
        bucketEntity.getItems()
                .stream()
                .filter(bookInBucketEntity -> Objects.equals(bookInBucketEntity.getItem().getId(), request.bookId()))
                .findFirst()
                .ifPresent(bookInBucketEntity -> {
                    throw new BadRequestException("Товар уже добавлен в корзину");
                });
        ItemInBucketEntity itemInBucketEntity = new ItemInBucketEntity();
        itemInBucketEntity.setItem(itemService.findEntityById(request.bookId()));
        itemInBucketEntity.setItemCount(1);
        bucketEntity.getItems().add(itemInBucketEntity);
        bucketRepository.save(bucketEntity);
    }
}
