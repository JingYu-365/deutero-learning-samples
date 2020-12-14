package com.github.laba.mockito.spying;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.MockitoAnnotations;
import org.mockito.Spy;
import org.mockito.runners.MockitoJUnitRunner;

import java.util.ArrayList;
import java.util.List;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.core.IsEqual.equalTo;

/**
 * 通过 注解方式实现 spy
 *
 * @author laba zhang
 */
@RunWith(MockitoJUnitRunner.class)
public class SpyAnnotationTest {

    @Spy
    private final List<String> list = new ArrayList<>();

    @Before
    public void init() {
        MockitoAnnotations.initMocks(this);
    }
    
    @Test
    public void testAnnotationSpy() {
        list.add("Mockito");
        list.add("PowerMock");

        // 通过 spy 操作 mock 出来的对象，会调用真实的方法
        assertThat(list.get(0), equalTo("Mockito"));
        assertThat(list.get(1), equalTo("PowerMock"));
        assertThat(list.isEmpty(), equalTo(false));
    }
}
