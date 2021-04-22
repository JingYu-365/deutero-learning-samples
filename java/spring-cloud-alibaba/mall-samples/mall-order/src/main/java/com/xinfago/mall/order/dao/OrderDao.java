package com.xinfago.mall.order.dao;

import com.xinfago.mall.order.entity.OrderEntity;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;

/**
 * 订单
 * 
 * @author xinfago
 * @email xinfago@163.com
 * @date 2021-04-18 14:46:08
 */
@Mapper
public interface OrderDao extends BaseMapper<OrderEntity> {
	
}
