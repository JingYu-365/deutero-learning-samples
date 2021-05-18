package com.xinfago.redis.repository;


import com.xinfago.redis.pojo.Article;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ArticleRepository extends CrudRepository<Article, Integer> {
}