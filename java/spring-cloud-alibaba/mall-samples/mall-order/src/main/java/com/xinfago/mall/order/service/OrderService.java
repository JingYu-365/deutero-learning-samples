package com.xinfago.mall.order.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.xinfago.common.utils.PageUtils;
import com.xinfago.mall.order.entity.OrderEntity;

import java.util.Map;

/**
 * 订单
 *
 * @author xinfago
 * @email xinfago@163.com
 * @date 2021-04-18 14:46:08
 */
public interface OrderService extends IService<OrderEntity> {

    PageUtils queryPage(Map<String, Object> params);
}

