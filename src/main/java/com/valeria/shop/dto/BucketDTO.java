package com.valeria.shop.dto;

import com.valeria.shop.db.entity.BucketState;
import com.valeria.shop.db.entity.DeliveryEntity;
import lombok.Data;

import java.util.List;

@Data
public class BucketDTO {
    private Long orderId;
    private List<ItemInBucketDTO> items;
    private BucketState state;
    private DeliveryEntity delivery;
}
