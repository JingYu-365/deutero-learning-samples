package com.xinfago.mall.product.dao;

import com.xinfago.mall.product.entity.CategoryEntity;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;

/**
 * 商品三级分类
 * 
 * @author xinfago
 * @email xinfago@163.com
 * @date 2021-04-18 14:48:11
 */
@Mapper
public interface CategoryDao extends BaseMapper<CategoryEntity> {
	
}
