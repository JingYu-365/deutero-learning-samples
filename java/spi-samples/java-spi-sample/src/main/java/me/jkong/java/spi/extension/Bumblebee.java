package me.jkong.java.spi.extension;

import me.jkong.java.spi.interfaces.Robot;

public class Bumblebee implements Robot {

    @Override
    public void sayHello() {
        System.out.println("Hello, I am Bumblebee.");
    }
}