package com.github.labazhang.mybatis.mapper;


import com.github.labazhang.mybatis.pojo.Article;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface ArticleMapper {
    public Article selectArticle(Integer id);
}