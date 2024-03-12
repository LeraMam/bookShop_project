package com.valeria.shop.config;

import com.valeria.shop.service.bucket.action.BucketAction;
import com.valeria.shop.service.bucket.action.BucketActionProcessor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.Collection;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;

@Configuration
public class BeanConfig {
    @Bean
    Map<BucketAction, BucketActionProcessor> bucketActionProcessorMap(Collection<BucketActionProcessor> processors) {
        return processors.stream().collect(Collectors.toMap(BucketActionProcessor::bucketAction, Function.identity()));
    }
}
