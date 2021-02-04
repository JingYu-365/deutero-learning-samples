package com.github.labazhang.disruptor;

public class LongEvent {
    private long value;

    public void set(long value) {
        this.value = value;
    }

    @Override
    public String toString() {
        final StringBuilder sb = new StringBuilder("LongEvent{");
        sb.append("value=").append(value);
        sb.append('}');
        return sb.toString();
    }
}