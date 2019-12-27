package me.jkong.redisson.config;

import org.redisson.Redisson;
import org.redisson.api.RedissonClient;
import org.redisson.api.RedissonReactiveClient;
import org.redisson.config.Config;

import java.io.File;
import java.io.IOException;

/**
 * @author JKong
 * @version v1.0
 * @description TODO
 * @date 2019/11/25 15:48.
 */
public class FileWayToConfigureRedisson {

    private static String REDISSON_JSON_CONFIG = "redisson-config.json";
    private static String REDISSON_YMAL_CONFIG = "redisson-config.json";

    public static RedissonReactiveClient instatnceAsync() throws IOException {

        Config config = getConfig();
        return Redisson.createReactive(config);
    }

    public static RedissonClient instatnce() throws IOException {

        Config config = getConfig();
        return Redisson.create(config);
    }

    private static Config getConfig() throws IOException {
        Config config = initFromJson();
        if (config == null) {
            config = initFromYmal();
        }

        if (config == null) {
            throw new NullPointerException("field to init redisson configuration from file.");
        }
        return config;
    }


    private static Config initFromJson() throws IOException {
        return Config.fromJSON(new File(REDISSON_JSON_CONFIG));
    }


    private static Config initFromYmal() throws IOException {
        return Config.fromYAML(new File(REDISSON_YMAL_CONFIG));
    }

}