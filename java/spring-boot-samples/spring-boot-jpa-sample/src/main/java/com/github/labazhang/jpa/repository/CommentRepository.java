package com.xinfago.jpa.repository;

import com.xinfago.jpa.pojo.Comment;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * Comment Repository
 *
 * @author laba zhang
 */
@Repository
public interface CommentRepository extends JpaRepository<Comment, Integer> {
}
