package com.github.laba.mockito.matcher;

import org.hamcrest.MatcherAssert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.mockito.runners.MockitoJUnitRunner;

import java.util.ArrayList;
import java.util.List;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.core.IsEqual.equalTo;

/**
 * test for arguments matcher
 *
 * @author laba zhang
 */
@RunWith(MockitoJUnitRunner.class)
public class ArgumentsMatcherTest {

    @Test
    public void testEqualMatcher() {
        List<Integer> list = Mockito.mock(ArrayList.class);
        Mockito.when(list.get(0)).thenReturn(100);
        // mockito#eq: 当传入的值为1时，返回定义的返回值，否则返回默认值。
        Mockito.when(list.get(Mockito.eq(1))).thenReturn(123);

        assertThat(list.get(0), equalTo(100));
        assertThat(list.get(1), equalTo(123));
        assertThat(list.get(2), equalTo(null));
    }

    @Test
    public void testComplex() {
        Foo foo = Mockito.mock(Foo.class);
        Mockito.when(foo.doWork(Mockito.isA(Parent.class))).thenReturn(100);
        MatcherAssert.assertThat(foo.doWork(new Child1()), equalTo(100));
        MatcherAssert.assertThat(foo.doWork(new Child2()), equalTo(100));
    }

    @Test
    public void testIsANotEqual() {
        Foo foo = Mockito.mock(Foo.class);
        Mockito.when(foo.doWork(Mockito.isA(Child1.class))).thenReturn(100);
        // 不符合条件时，返回数据类型的默认值
        MatcherAssert.assertThat(foo.doWork(new Child2()), equalTo(0));
    }

    @Test
    public void testIsAny() {
        Foo foo = Mockito.mock(Foo.class);
        // any中会指定参数泛型，在语法层面做限制
        Mockito.when(foo.doWork(Mockito.any(Child1.class))).thenReturn(100);
        // 通过语法校验就可以通过测试
        MatcherAssert.assertThat(foo.doWork(new Child2()), equalTo(100));
    }

    static class Foo {
        int doWork(Parent p) {
            return p.doWork();
        }
    }

    static interface Parent {
        /**
         * do work
         *
         * @return int
         */
        int doWork();
    }

    static class Child1 implements Parent {

        @Override
        public int doWork() {
            return 1;
        }
    }

    static class Child2 implements Parent {

        @Override
        public int doWork() {
            return 2;
        }
    }
}
