package me.jkong.redisson.config;

import org.redisson.config.Config;
import org.redisson.config.TransportMode;

/**
 * @author JKong
 * @version v1.0
 * @description 通过 class 配置 Redisson
 * @date 2019/11/25 15:48.
 */
public class ClassWayToConfigureRedisson {

    public static Config instatnceAsync() {
        Config config = new Config();
        config.setTransportMode(TransportMode.EPOLL)
                .useClusterServers()
                //可以用"rediss://"来启用SSL连接
                .addNodeAddress("redis://127.0.0.1:7181");
        return config;
    }


}