package me.jkong.pay.dao;

import me.jkong.entities.Payment;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

/**
 * @author JKong
 * @version v1.0
 * @description 持久层
 * @date 2020-06-08 23:14.
 */
@Mapper
public interface PaymentDao {

    /**
     * 创建订单
     *
     * @param payment 订单数据
     * @return num
     */
    int create(Payment payment);

    /**
     * 根据ID获取订单信息
     *
     * @param id id
     * @return Payment
     */
    Payment getPaymentById(@Param("id") Long id);
}