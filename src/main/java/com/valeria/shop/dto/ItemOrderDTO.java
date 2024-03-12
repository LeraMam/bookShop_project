package com.valeria.shop.dto;

import com.valeria.shop.db.entity.BucketState;
import com.valeria.shop.db.entity.DeliveryEntity;
import lombok.Data;

import java.util.ArrayList;
import java.util.List;

@Data
public class ItemOrderDTO {
    private Long id;
    private List<Long> books = new ArrayList<>();
    private BucketState STATE;
    private DeliveryEntity delivery;
}
