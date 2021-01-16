package com.test;

import com.github.labazhang.service.StuService;
import com.github.labazhang.service.TestTransService;
import org.springframework.beans.factory.annotation.Autowired;

//@RunWith(SpringRunner.class)
//@SpringBootTest(classes = Application.class)
public class TransTest {

    @Autowired
    private StuService stuService;

    @Autowired
    private TestTransService testTransService;

//    @Test
    public void myTest() {
//        stuService.testPropagationTrans();
        testTransService.testPropagationTrans();
    }

}
