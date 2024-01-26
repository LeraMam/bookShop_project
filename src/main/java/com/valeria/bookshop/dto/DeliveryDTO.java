package com.valeria.bookshop.dto;

import lombok.Data;

@Data
public class DeliveryDTO {
    private Long id;
    private String postIndex;
    private String phone;
    private String region;
    private String city;
    private String street;
    private String house;
    private String flat;
}
