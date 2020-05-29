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
 * @description 文件方式进行实例化Redisson
 * @date 2019/11/25 15:48.
 */
public class FileWayToConfigureRedisson {

    private static String REDISSON_JSON_CONFIG = "redisson-config.json";
    private static String REDISSON_YMAL_CONFIG = "redisson-config.yaml";

    public static RedissonReactiveClient instanceAsync() throws IOException {
        Config config = getConfig();
        return Redisson.createReactive(config);
    }

    public static RedissonClient instance() throws IOException {
        Config config = getConfig();
        return Redisson.create(config);
    }

    private static Config getConfig() throws IOException {
        Config config = initFromJson();
        if (config == null) {
            config = initFromYaml();
        }
        if (config == null) {
            throw new NullPointerException("field to init redisson configuration from file.");
        }
        return config;
    }

    /**
     * 使用 json 进行 Redisson 实例化
     *
     * @return Redisson Config
     * @throws IOException e
     */
    private static Config initFromJson() throws IOException {
        return Config.fromJSON(new File(REDISSON_JSON_CONFIG));
    }


    /**
     * 使用 yaml 进行 Redisson 实例化
     *
     * @return Redisson Config
     * @throws IOException e
     */
    private static Config initFromYaml() throws IOException {
        return Config.fromYAML(new File(REDISSON_YMAL_CONFIG));
    }

}