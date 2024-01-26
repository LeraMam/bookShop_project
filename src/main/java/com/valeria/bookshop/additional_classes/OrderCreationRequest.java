package com.valeria.bookshop.additional_classes;

import com.valeria.bookshop.db.entity.BucketEntity;
import com.valeria.bookshop.db.entity.DeliveryEntity;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor(force = true)   //обертка для двух классов что бы запихнуть их в запрос
@AllArgsConstructor
public class OrderCreationRequest {
    private final BucketEntity bookOrder;
    private final DeliveryEntity delivery;
}
