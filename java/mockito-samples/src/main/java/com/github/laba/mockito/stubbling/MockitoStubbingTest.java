package com.github.laba.mockito.stubbling;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.mockito.runners.MockitoJUnitRunner;

import java.util.ArrayList;

import static junit.framework.TestCase.assertEquals;
import static org.mockito.Mockito.when;

/**
 * mockito stubbing test
 *
 * @author laba zhang
 */
@RunWith(MockitoJUnitRunner.class)
public class MockitoStubbingTest {

    private ArrayList<String> list;

    @Before
    public void init() {
        this.list = (ArrayList<String>) Mockito.mock(ArrayList.class);
    }

    @Test
    public void stubbingForRealCall() {
        // mock 出的对象，并不会真正去调用我们定义的方法，原因是因为mock出的对象是 mockito 通过动态代理实现的类的对象
        StubbingService service = Mockito.mock(StubbingService.class);
        when(service.getS()).thenReturn("mockito s");
        assertEquals(service.getS(), "mockito s");

        when(service.getI()).thenCallRealMethod();
        assertEquals(service.getI(), 10);
    }
}
