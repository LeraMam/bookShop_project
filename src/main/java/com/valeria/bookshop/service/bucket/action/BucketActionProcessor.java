package com.valeria.bookshop.service.bucket.action;

import com.valeria.bookshop.db.entity.BucketEntity;
import com.valeria.bookshop.request.BucketActionRequest;

public interface BucketActionProcessor {
    BucketAction bucketAction();
    void process(BucketEntity bucketEntity, BucketActionRequest request);
}
