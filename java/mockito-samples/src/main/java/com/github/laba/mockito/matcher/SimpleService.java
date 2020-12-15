package com.github.laba.mockito.matcher;

import java.io.Serializable;
import java.util.Collection;

/**
 * fro wildcard test
 *
 * @author laba zhang
 */
public class SimpleService {
    public int intMethod(int i, String s, Collection<?> c, Serializable ser) {
        throw new RuntimeException();
    }

    public void voidMethod(int i, String s, Collection<?> c, Serializable ser) {
        throw new RuntimeException();
    }
}
