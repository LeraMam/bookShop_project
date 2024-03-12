package com.valeria.shop.additional_classes;

import com.valeria.shop.dto.BucketDTO;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor(force = true)   //обертка для двух классов что бы запихнуть их в запрос
@AllArgsConstructor
public class BuckerReadingRequest {
    private List<String> states;
    private List<BucketDTO> buckets;
}
