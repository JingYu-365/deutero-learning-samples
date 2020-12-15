package com.github.laba.mockito.matcher;

import org.junit.Test;

import java.util.stream.Stream;

import static org.hamcrest.CoreMatchers.*;
import static org.junit.Assert.assertThat;

/**
 * test for assert match
 *
 * @author laba zhang
 */
public class AssertMatcherTest {

    @Test
    public void test() {
        int i = 10;
        assertThat(i, equalTo(10));
        assertThat(i, not(equalTo(20)));

        assertThat(i, is(10));
        assertThat(i, is(not(20)));

        double price = 23.45;
        // 满足其中之一
        assertThat(price, either(equalTo(23.45)).or(equalTo(23.54)));
        // 条件都满足
        assertThat(price, both(equalTo(23.45)).and(not(equalTo(23.54))));
        //满足其中之一
        assertThat(price, anyOf(equalTo(23.45), not(equalTo(23.54)), is(123.34)));
        // 添加错误提示信息
//        assertThat("assert double price failed.", price, both(equalTo(23.45)).and(equalTo(23.54)));

        assertThat(Stream.of(1, 2, 3, 4).anyMatch(j -> j > 2), equalTo(true));
        assertThat(Stream.of(1, 2, 3, 4).allMatch(j -> j < 2), equalTo(false));

    }
}
