package me.jkong.resteasy;

import com.google.inject.Guice;
import com.google.inject.Injector;
import me.jkong.resteasy.controller.HelloController;
import me.jkong.resteasy.server.RestModule;
import me.jkong.resteasy.server.RestNettyServer;

import java.util.ArrayList;
import java.util.Collection;

/**
 * 启动类
 *
 * @author JKong
 * @date 2019/11/11 14:12.
 */
public class Application {

    public static void main(String[] args) {
        RestNettyServer restNettyServer = new RestNettyServer();
        restNettyServer.addResources(getResources());
        restNettyServer.start();
    }

    /**
     * 配置controller
     *
     * @return 对象List
     */
    private static Collection<Object> getResources() {
        Collection<Object> collection = new ArrayList<>();
        Injector injector = Guice.createInjector(new RestModule());
        collection.add(injector.getInstance(HelloController.class));
        return collection;
    }
}