package com.xinfago.spa.nacos.dubbo.provider.impl;

import com.xinfago.spa.nacos.dubbo.api.service.ProductService;
import org.apache.dubbo.config.annotation.DubboService;

/**
 * TODO
 *
 * @author Zhang Duanhe
 * @since 2021/10/12
 */
@DubboService(version = "1.0.0", group = "product")
public class ProductServiceImpl implements ProductService {
    @Override
    public String getProductInfo(String msg) {
        return "hello," + msg;
    }
}