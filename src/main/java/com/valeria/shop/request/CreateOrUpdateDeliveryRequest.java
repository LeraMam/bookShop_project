package com.valeria.shop.request;

public record CreateOrUpdateDeliveryRequest(String postIndex, String phone,
                                            String region, String city,
                                            String street, String house, String flat) {
}
