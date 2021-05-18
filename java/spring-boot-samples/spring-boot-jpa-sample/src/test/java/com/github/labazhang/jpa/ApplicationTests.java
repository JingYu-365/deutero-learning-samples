package com.xinfago.jpa;

import com.xinfago.jpa.repository.CommentRepository;
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
    void testForComment() {
        System.out.println(commentRepository.findById(1));
    }

}
