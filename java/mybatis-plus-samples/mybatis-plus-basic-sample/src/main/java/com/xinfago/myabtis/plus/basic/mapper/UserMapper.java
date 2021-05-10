package com.xinfago.myabtis.plus.basic.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.xinfago.myabtis.plus.basic.entity.User;
import org.apache.ibatis.annotations.Mapper;

/**
 * User 数据库操作
 *
 * @author xinfago
 */
@Mapper
public interface UserMapper extends BaseMapper<User> {
}
