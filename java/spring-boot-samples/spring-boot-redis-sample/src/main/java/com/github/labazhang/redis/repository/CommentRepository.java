package com.github.labazhang.redis.repository;

import com.github.labazhang.redis.pojo.Comment;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

/**
 * Comment Repository
 *
 * @author laba zhang
 */
@Repository
public interface CommentRepository extends CrudRepository<Comment, Integer> {
}
