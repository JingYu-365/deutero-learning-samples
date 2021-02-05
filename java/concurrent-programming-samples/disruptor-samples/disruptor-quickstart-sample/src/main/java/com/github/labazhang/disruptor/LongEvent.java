package com.github.labazhang.disruptor;

public class LongEvent {
    private long value;

    public void set(long value) {
        this.value = value;
    }

    @Override
    public String toString() {
        return "value=" + value;
    }
}