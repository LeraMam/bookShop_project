package com.valeria.bookshop.service.bucket.action;

import com.valeria.bookshop.db.entity.BookInBucketEntity;
import com.valeria.bookshop.db.entity.BucketEntity;
import com.valeria.bookshop.db.repository.BucketRepository;
import com.valeria.bookshop.exception.BadRequestException;
import com.valeria.bookshop.request.BucketActionRequest;
import com.valeria.bookshop.service.BookService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.Objects;

@Component
@RequiredArgsConstructor
public class AppendBookBucketActionProcessor implements BucketActionProcessor {
    private final BucketRepository bucketRepository;
    private final BookService bookService;

    @Override
    public BucketAction bucketAction() {
        return BucketAction.APPEND_BOOK;
    }

    @Override
    public void process(BucketEntity bucketEntity, BucketActionRequest request) {
        bucketEntity.getBooks()
                .stream()
                .filter(bookInBucketEntity -> Objects.equals(bookInBucketEntity.getBook().getId(), request.bookId()))
                .findFirst()
                .ifPresent(bookInBucketEntity -> {
                    throw new BadRequestException("Товар уже добавлен в корзину");
                });
        BookInBucketEntity bookInBucketEntity = new BookInBucketEntity();
        bookInBucketEntity.setBook(bookService.findEntityById(request.bookId()));
        bookInBucketEntity.setBookCount(1);
        bucketEntity.getBooks().add(bookInBucketEntity);
        bucketRepository.save(bucketEntity);
    }
}
