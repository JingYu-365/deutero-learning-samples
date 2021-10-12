package com.xinfago.spa.feign.consumer.service;

import com.xinfago.spa.feign.consumer.service.impl.FallbackServiceImpl;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

/**
 * @author Zhang Duanhe
 */
@FeignClient(value = "nacos-provider", fallback = FallbackServiceImpl.class)
public interface FeignService {

    @GetMapping(value = "/test/{message}")
    String test(@PathVariable("message") String message);

    @GetMapping(value = "/hi")
    String hi();
}