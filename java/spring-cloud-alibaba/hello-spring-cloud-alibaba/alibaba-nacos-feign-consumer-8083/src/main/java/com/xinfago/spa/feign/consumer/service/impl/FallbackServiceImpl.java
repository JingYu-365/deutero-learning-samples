package com.xinfago.spa.feign.consumer.service.impl;

import com.xinfago.spa.feign.consumer.service.FeignService;
import org.springframework.stereotype.Component;

@Component
public class FallbackServiceImpl implements FeignService {

    @Override
    public String test(String message) {
        return "test fallback";
    }

    @Override
    public String hi() {
        return "test fallback";
    }
}