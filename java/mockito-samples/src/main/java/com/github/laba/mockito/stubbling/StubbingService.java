package com.github.laba.mockito.stubbling;

/**
 * test for stubbing invoke real method
 *
 * @author laba zhang
 */
public class StubbingService {
    public int getI() {
        return 10;
    }

    public String getS() {
        throw new IllegalArgumentException();
    }
}
