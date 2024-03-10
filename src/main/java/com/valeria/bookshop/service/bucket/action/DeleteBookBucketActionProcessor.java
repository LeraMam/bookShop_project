package com.valeria.bookshop.service.bucket.action;

import com.valeria.bookshop.db.entity.ItemInBucketEntity;
import com.valeria.bookshop.db.entity.BucketEntity;
import com.valeria.bookshop.db.repository.ItemInBucketRepository;
import com.valeria.bookshop.db.repository.BucketRepository;
import com.valeria.bookshop.exception.BadRequestException;
import com.valeria.bookshop.request.BucketActionRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.Objects;

@Component
@RequiredArgsConstructor
public class DeleteBookBucketActionProcessor implements BucketActionProcessor {
    private final BucketRepository bucketRepository;
    private final ItemInBucketRepository itemInBucketRepository;

    @Override
    public BucketAction bucketAction() {
        return BucketAction.DELETE_ITEM;
    }

    @Override
    public void process(BucketEntity bucketEntity, BucketActionRequest request) {
        ItemInBucketEntity itemInBucket = bucketEntity.getItems()
                .stream()
                .filter(bookInBucketEntity -> Objects.equals(bookInBucketEntity.getItem().getId(), request.bookId()))
                .findFirst()
                .orElseThrow(() -> new BadRequestException("Товара нет в корзине"));
        bucketEntity.getItems().remove(itemInBucket);
        bucketRepository.save(bucketEntity);
        itemInBucketRepository.delete(itemInBucket);
    }
}
