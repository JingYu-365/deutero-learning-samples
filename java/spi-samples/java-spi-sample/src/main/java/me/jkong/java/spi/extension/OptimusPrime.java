package me.jkong.java.spi.extension;

import me.jkong.java.spi.interfaces.Robot;

public class OptimusPrime implements Robot {
    
    @Override
    public void sayHello() {
        System.out.println("Hello, I am Optimus Prime.");
    }
}