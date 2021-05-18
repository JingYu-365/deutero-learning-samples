package com.xinfago.mybatis.mapper;


import com.xinfago.mybatis.pojo.Article;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface ArticleMapper {
    public Article selectArticle(Integer id);
}