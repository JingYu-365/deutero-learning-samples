package com.xinfago.spa.nacos.dubbo.consumer.controller;

import com.xinfago.spa.nacos.dubbo.api.service.ProductService;
import org.apache.dubbo.config.annotation.DubboReference;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 *
 */
@RestController
public class ProductController {

    @DubboReference(check = false, version = "1.0.0", group = "product")
    private ProductService productService;


    @GetMapping("/product")
    public String getProductInfo(String msg) {
        return this.productService.getProductInfo(msg);
    }
}