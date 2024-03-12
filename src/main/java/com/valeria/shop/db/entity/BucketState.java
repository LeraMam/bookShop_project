package com.valeria.shop.db.entity;

public enum BucketState {
    OPEN, SUBMITTED, IN_PROCESSING, RESOLVED;
}

//у пользователя: SUBMITTED-ожидает подтверждения, IN_PROCESSING-в обработке или доставляется, RESOLVED-доставлен