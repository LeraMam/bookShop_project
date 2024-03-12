package com.valeria.shop.request;

import com.valeria.shop.db.entity.BucketState;

public record ChangeBucketStateRequest(BucketState state) {
}
