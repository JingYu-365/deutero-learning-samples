package com.github.labazhang.redis;

import com.github.labazhang.redis.pojo.Comment;
import com.github.labazhang.redis.repository.CommentRepository;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
@SpringBootTest
class ApplicationTests {

    @Autowired
    private CommentRepository commentRepository;

    @Test
    void testForSaveComment() {
        Comment comment = new Comment();
        comment.setId(1);
        comment.setAuthor("laba");
        comment.setContent("hello redis");
        commentRepository.save(comment);

        System.out.println(commentRepository.findById(1));
    }

}
