package com.xinfago.mall.ware.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.xinfago.common.utils.PageUtils;
import com.xinfago.mall.ware.entity.WareOrderTaskDetailEntity;

import java.util.Map;

/**
 * 库存工作单
 *
 * @author xinfago
 * @email xinfago@163.com
 * @date 2021-04-18 14:52:22
 */
public interface WareOrderTaskDetailService extends IService<WareOrderTaskDetailEntity> {

    PageUtils queryPage(Map<String, Object> params);
}

