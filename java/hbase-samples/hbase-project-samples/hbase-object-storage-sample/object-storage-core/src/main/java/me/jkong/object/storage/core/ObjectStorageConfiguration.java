package me.jkong.object.storage.core;

import org.springframework.core.io.Resource;
import org.springframework.core.io.support.PathMatchingResourcePatternResolver;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

/**
 * 对象存储配置中心
 *
 * @author JKong
 * @version v0.0.1
 * @date 2020/8/22 14:41.
 */
public class ObjectStorageConfiguration {
    private volatile static ObjectStorageConfiguration configuration;
    private static Properties properties;

    static {
        try {
            init();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * 加载 resources 目录下的所有 *.properties 配置文件
     *
     * @throws IOException 解析异常时抛出异常
     */
    private static void init() throws IOException {
        PathMatchingResourcePatternResolver resolver = new PathMatchingResourcePatternResolver();
        properties = new Properties();
        Resource[] resources = resolver.getResources("classpath:*.properties");
        Properties properties;
        for (Resource resource : resources) {
            InputStream inputStream = resource.getInputStream();
            properties = new Properties();
            properties.load(inputStream);
            ObjectStorageConfiguration.properties.putAll(properties);
        }

    }

    private ObjectStorageConfiguration() {
    }

    public static ObjectStorageConfiguration getInstance() {
        if (configuration == null) {
            synchronized (ObjectStorageConfiguration.class) {
                if (configuration == null) {
                    configuration = new ObjectStorageConfiguration();
                }
            }
        }
        return configuration;
    }

    public Integer getInteger(String key) {
        return Integer.parseInt(properties.getProperty(key));
    }

    public String getString(String key) {
        return properties.getProperty(key);
    }

    public boolean getBoolean(String key) {
        return Boolean.parseBoolean(properties.getProperty(key));
    }

    public long getLong(String key) {
        return Long.parseLong(properties.getProperty(key));
    }
}
