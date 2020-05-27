package me.jkong.dubbo.spi.extension;

import me.jkong.dubbo.spi.interfaces.Robot;

public class OptimusPrime implements Robot {
    
    @Override
    public void sayHello() {
        System.out.println("Hello, I am Optimus Prime.");
    }
}