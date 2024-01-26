package com.valeria.bookshop.dto;

import com.valeria.bookshop.db.entity.BucketState;
import com.valeria.bookshop.db.entity.DeliveryEntity;
import lombok.Data;

import java.util.List;

@Data
public class BucketDTO {
    private Long orderId;
    private List<BookInBucketDTO> books;
    private BucketState state;
    private DeliveryEntity delivery;
}
