package com.valeria.shop.request;

import com.valeria.shop.service.bucket.action.BucketAction;

public record BucketActionRequest(BucketAction action, Long bookId) {
}
