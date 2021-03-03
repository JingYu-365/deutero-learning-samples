package com.github.labazhang.redis.repository;


import com.github.labazhang.redis.pojo.Article;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ArticleRepository extends CrudRepository<Article, Integer> {
}