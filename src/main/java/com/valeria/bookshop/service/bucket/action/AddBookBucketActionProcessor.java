package com.valeria.bookshop.service.bucket.action;

import com.valeria.bookshop.db.entity.ItemInBucketEntity;
import com.valeria.bookshop.db.entity.BucketEntity;
import com.valeria.bookshop.db.repository.BucketRepository;
import com.valeria.bookshop.exception.BadRequestException;
import com.valeria.bookshop.request.BucketActionRequest;
import com.valeria.bookshop.service.ItemService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.Objects;

@Component
@RequiredArgsConstructor
public class AddBookBucketActionProcessor implements BucketActionProcessor {
    private final BucketRepository bucketRepository;
    private final ItemService itemService;

    @Override
    public BucketAction bucketAction() {
        return BucketAction.ADD_BOOK;
    }

    @Override
    public void process(BucketEntity bucketEntity, BucketActionRequest request) {
        ItemInBucketEntity itemInBucket = bucketEntity.getItems()
                .stream()
                .filter(bookInBucketEntity -> Objects.equals(bookInBucketEntity.getItem().getId(), request.bookId()))
                .findFirst()
                .orElseThrow(() -> new BadRequestException("Товара нет в корзине"));
        itemInBucket.setItemCount(itemInBucket.getItemCount() + 1);
        bucketRepository.save(bucketEntity);
    }
}
