package com.valeria.bookshop.request;

import com.valeria.bookshop.service.bucket.action.BucketAction;

public record BucketActionRequest(BucketAction action, Long bookId) {
}
