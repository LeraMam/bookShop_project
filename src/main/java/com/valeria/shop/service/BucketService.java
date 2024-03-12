package com.valeria.shop.service;

import com.valeria.shop.db.entity.BucketEntity;
import com.valeria.shop.db.entity.BucketState;
import com.valeria.shop.db.entity.DeliveryEntity;
import com.valeria.shop.db.entity.UserEntity;
import com.valeria.shop.db.repository.BucketRepository;
import com.valeria.shop.db.repository.DeliveryRepository;
import com.valeria.shop.dto.BucketDTO;
import com.valeria.shop.exception.BadRequestException;
import com.valeria.shop.exception.NotFoundException;
import com.valeria.shop.mapper.BucketMapper;
import com.valeria.shop.request.BucketActionRequest;
import com.valeria.shop.service.bucket.action.BucketAction;
import com.valeria.shop.service.bucket.action.BucketActionProcessor;
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
    private final ItemService itemService;
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
        if (bucketEntity.getItems().isEmpty()) throw new BadRequestException("Корзина пуста");
        bucketEntity.setState(BucketState.SUBMITTED);
        bucketRepository.save(bucketEntity);
    }

    public List<BucketDTO> getBuckets(BucketState bucketState) {
        return bucketMapper.mapToDTOList(bucketRepository.findAllByState(bucketState));
    }
    public List<BucketDTO> getBucketsForCurrentUser() {
        return bucketMapper.mapToDTOList(bucketRepository.findAllByUserEntity(userService.getCurrentAuthorizedUserEntity()));
    }

    public List<BucketDTO> getBucketsForAllUsers() {
        return bucketMapper.mapToDTOList(bucketRepository.findAll());
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
                    newBucket.setItems(new ArrayList<>());
                    newBucket.setUserEntity(user);
                    return bucketRepository.save(newBucket);
                } );
    }

    public List<BucketDTO> getAllBucketsForAnalyticsUser() {
        List<BucketDTO> list = bucketMapper.mapToDTOList(bucketRepository.findAll());
        List<Long> delete = new ArrayList<>();
        for(BucketDTO element : list){

        }
        return list;
    }

    public void deleteOrder(Long orderId) {
        bucketRepository.deleteById(orderId);
    }
}
