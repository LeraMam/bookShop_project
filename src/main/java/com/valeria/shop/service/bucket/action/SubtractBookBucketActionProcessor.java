package com.valeria.shop.service.bucket.action;

import com.valeria.shop.db.entity.ItemInBucketEntity;
import com.valeria.shop.db.entity.BucketEntity;
import com.valeria.shop.db.repository.BucketRepository;
import com.valeria.shop.exception.BadRequestException;
import com.valeria.shop.request.BucketActionRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.Objects;

@Component
@RequiredArgsConstructor
public class SubtractBookBucketActionProcessor implements BucketActionProcessor {
    private final BucketRepository bucketRepository;

    @Override
    public BucketAction bucketAction() {
        return BucketAction.SUBTRACT_ITEM;
    }

    @Override
    public void process(BucketEntity bucketEntity, BucketActionRequest request) {
        ItemInBucketEntity itemInBucket = bucketEntity.getItems()
                .stream()
                .filter(bookInBucketEntity -> Objects.equals(bookInBucketEntity.getItem().getId(), request.bookId()))
                .findFirst()
                .orElseThrow(() -> new BadRequestException("Товара нет в корзине"));
        if(itemInBucket.getItemCount() > 1) {
            itemInBucket.setItemCount(itemInBucket.getItemCount() - 1);
        }
        bucketRepository.save(bucketEntity);
    }
}
