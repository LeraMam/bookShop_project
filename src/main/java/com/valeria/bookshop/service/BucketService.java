package com.valeria.bookshop.service;

import com.valeria.bookshop.db.entity.BucketEntity;
import com.valeria.bookshop.db.entity.BucketState;
import com.valeria.bookshop.db.entity.DeliveryEntity;
import com.valeria.bookshop.db.entity.UserEntity;
import com.valeria.bookshop.db.repository.BucketRepository;
import com.valeria.bookshop.db.repository.DeliveryRepository;
import com.valeria.bookshop.dto.BucketDTO;
import com.valeria.bookshop.exception.BadRequestException;
import com.valeria.bookshop.exception.NotFoundException;
import com.valeria.bookshop.mapper.BucketMapper;
import com.valeria.bookshop.request.BucketActionRequest;
import com.valeria.bookshop.service.bucket.action.BucketAction;
import com.valeria.bookshop.service.bucket.action.BucketActionProcessor;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@Service
@RequiredArgsConstructor
@Transactional
public class BucketService {
    private final UserService userService;
    private final BucketRepository bucketRepository;
    private final BookService bookService;
    private final DeliveryRepository deliveryRepository;
    private final BucketMapper bucketMapper;
    private final Map<BucketAction, BucketActionProcessor> bucketActionProcessorMap;

    public void applyBucketAction(BucketActionRequest request) {
        BucketEntity bucketEntity = getOrCreateBucketEntity();
        bucketActionProcessorMap.get(request.action()).process(bucketEntity, request);
    }

    public void submitBucket(DeliveryEntity deliveryEntity) {
        BucketEntity bucketEntity = getOrCreateBucketEntity();
        bucketEntity.setDelivery(deliveryEntity);
        if (bucketEntity.getBooks().isEmpty()) throw new BadRequestException("Корзина пуста");
        bucketEntity.setState(BucketState.SUBMITTED);
        bucketRepository.save(bucketEntity);
    }

    public List<BucketDTO> getBuckets(BucketState bucketState) {
        return bucketMapper.mapToDTOList(bucketRepository.findAllByState(bucketState));
    }
    public List<BucketDTO> getBucketsForCurrentUser() {
        return bucketMapper.mapToDTOList(bucketRepository.findAllByUserEntity(userService.getCurrentAuthorizedUserEntity()));
    }

    public void changeBucketState(Long bucketId, BucketState bucketState) {
        BucketEntity bucketEntity = bucketRepository.findById(bucketId)
                .orElseThrow(() -> new NotFoundException("Корзина не найдена"));
        bucketEntity.setState(bucketState);
        bucketRepository.save(bucketEntity);
    }

    public BucketDTO getCurrentBucket() {
        return bucketMapper.mapToDTO(getOrCreateBucketEntity());
    }

    private BucketEntity getOrCreateBucketEntity() {
        UserEntity user = userService.getCurrentAuthorizedUserEntity();
        return bucketRepository.findByUserEntityAndState(user, BucketState.OPEN)
                .orElseGet(() -> {
                    BucketEntity newBucket = new BucketEntity();
                    newBucket.setState(BucketState.OPEN);
                    newBucket.setBooks(new ArrayList<>());
                    newBucket.setUserEntity(user);
                    return bucketRepository.save(newBucket);
                });
    }

    public void deleteOrder(Long orderId) {
        bucketRepository.deleteById(orderId);
    }
/*
    @Transactional //add updating of book list
    public void updateOrder(Long orderId, BookOrderState state) {
        OrderEntity bookOrder = bucketRepository.findById(orderId).orElseThrow(
                () -> new IllegalStateException("bookOrder with id " + orderId + " does not exist")
        );
        if(state != null && !Objects.equals(bookOrder.getSTATE(), state)) {
            bookOrder.setSTATE(state);
        }
    }*/
}
