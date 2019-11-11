package me.jkong.resteasy.server;

import com.google.inject.AbstractModule;
import me.jkong.resteasy.controller.HelloController;

public class RestModule extends AbstractModule {
    @Override
    protected void configure() {
        bind(HelloController.class);
    }
}