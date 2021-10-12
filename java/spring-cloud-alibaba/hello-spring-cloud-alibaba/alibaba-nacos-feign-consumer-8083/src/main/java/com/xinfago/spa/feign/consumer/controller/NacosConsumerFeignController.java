package com.xinfago.spa.feign.consumer.controller;

import com.xinfago.spa.feign.consumer.service.FeignService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class NacosConsumerFeignController {

    @Autowired
    private FeignService feignService;

    @GetMapping(value = "/test/hi")
    public String test() {
        return feignService.test("Hi Feign");
    }

    @GetMapping(value = "/hi")
    public String hi() {
        return feignService.hi();
    }
}