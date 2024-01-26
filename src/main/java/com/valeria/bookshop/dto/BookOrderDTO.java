package com.valeria.bookshop.dto;

import com.valeria.bookshop.db.entity.BucketState;
import com.valeria.bookshop.db.entity.DeliveryEntity;
import lombok.Data;

import java.util.ArrayList;
import java.util.List;

@Data
public class BookOrderDTO {
    private Long id;
    private List<Long> books = new ArrayList<>();
    private BucketState STATE;
    private DeliveryEntity delivery;
}
