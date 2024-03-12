package com.valeria.shop.service.bucket.action;

import com.valeria.shop.db.entity.BucketEntity;
import com.valeria.shop.request.BucketActionRequest;

public interface BucketActionProcessor {
    BucketAction bucketAction();
    void process(BucketEntity bucketEntity, BucketActionRequest request);
}
