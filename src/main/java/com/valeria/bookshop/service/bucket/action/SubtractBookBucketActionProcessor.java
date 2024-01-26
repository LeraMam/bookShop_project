package com.valeria.bookshop.service.bucket.action;

import com.valeria.bookshop.db.entity.BookInBucketEntity;
import com.valeria.bookshop.db.entity.BucketEntity;
import com.valeria.bookshop.db.repository.BucketRepository;
import com.valeria.bookshop.exception.BadRequestException;
import com.valeria.bookshop.request.BucketActionRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.Objects;

@Component
@RequiredArgsConstructor
public class SubtractBookBucketActionProcessor implements BucketActionProcessor {
    private final BucketRepository bucketRepository;

    @Override
    public BucketAction bucketAction() {
        return BucketAction.SUBTRACT_BOOK;
    }

    @Override
    public void process(BucketEntity bucketEntity, BucketActionRequest request) {
        BookInBucketEntity bookInBucket = bucketEntity.getBooks()
                .stream()
                .filter(bookInBucketEntity -> Objects.equals(bookInBucketEntity.getBook().getId(), request.bookId()))
                .findFirst()
                .orElseThrow(() -> new BadRequestException("Cannot find book in bucket"));
        if(bookInBucket.getBookCount() > 1) {
            bookInBucket.setBookCount(bookInBucket.getBookCount() - 1);
        }
        bucketRepository.save(bucketEntity);
    }
}
