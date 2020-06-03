package me.jkong.dynamic.proxy.jdk;

/**
 * 实现类
 *
 * @author JKong
 */
public class TargetImpl implements Target {
    @Override
    public void work() {
        System.out.println("我就只能做这么多了");
    }
}