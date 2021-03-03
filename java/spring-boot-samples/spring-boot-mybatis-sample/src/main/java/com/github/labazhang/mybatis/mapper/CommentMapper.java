package com.github.labazhang.mybatis.mapper;

import com.github.labazhang.jpa.pojo.Comment;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

/**
 * Comment Mapper
 *
 * @author laba zhang
 */
@Mapper
public interface CommentMapper {

    @Select("SELECT * FROM t_comment WHERE id =#{id}")
    public Comment findById(Integer id);
}
