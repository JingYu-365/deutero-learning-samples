package com.github.labazhang.mybatis;

import com.github.labazhang.mybatis.mapper.ArticleMapper;
import com.github.labazhang.mybatis.mapper.CommentMapper;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
@SpringBootTest
class ApplicationTests {

    @Autowired
    private CommentMapper commentMapper;

    @Autowired
    private ArticleMapper articleMapper;

    @Test
    void testForComment() {
        System.out.println(commentMapper.findById(1));
    }

    @Test
    void testForArticle() {
        System.out.println(articleMapper.selectArticle(1));
    }

}
