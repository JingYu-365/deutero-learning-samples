package me.jkong.pay.service;

import me.jkong.entities.Payment;
import org.apache.ibatis.annotations.Param;

/**
 * 支付service
 * @author JKong
 */
public interface PaymentService {
    /**
     * 创建支付订单
     *
     * @param payment 订单信息
     * @return i
     */
    int create(Payment payment);

    /**
     * 查询支付详情
     *
     * @param id id
     * @return 订单详情
     */
    Payment getPaymentById(@Param("id") Long id);
}
