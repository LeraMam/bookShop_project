package com.valeria.bookshop.request;

import com.valeria.bookshop.db.entity.BucketState;

public record ChangeBucketStateRequest(BucketState state) {
}
