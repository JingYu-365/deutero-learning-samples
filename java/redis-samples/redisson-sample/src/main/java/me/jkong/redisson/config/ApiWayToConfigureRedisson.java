package me.jkong.redisson.config;

import org.redisson.config.Config;
import org.redisson.config.TransportMode;

/**
 * @author JKong
 * @version v1.0
 * @description 通过 class 配置 Redisson
 * @date 2019/11/25 15:48.
 */
public class ApiWayToConfigureRedisson {

    public static Config singleInstanceAsync(String ip, int port) {
        if (ip == null || ip.trim().length() == 0) {
            throw new IllegalArgumentException("ip is null.");
        }

        if (port <= 0 || port > 65535) {
            throw new IllegalArgumentException("port is illegal, port range is 1~65535");
        }
        return singleInstanceAsync(ip + ":" + port);
    }

    public static Config singleInstanceAsync(String addr) {
        Config config = new Config();
        config.setTransportMode(TransportMode.NIO)
                .useSingleServer().setAddress("redis://" + addr).setDatabase(1);
        return config;
    }


}