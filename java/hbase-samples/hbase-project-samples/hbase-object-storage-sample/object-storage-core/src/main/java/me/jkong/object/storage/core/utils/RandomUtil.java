package me.jkong.object.storage.core.utils;

import java.util.UUID;

/**
 * 随机生成工具路
 *
 * @author JKong
 * @version v0.0.1
 * @date 2020/8/22 15:07.
 */
public class RandomUtil {
    public static String getUUID() {
        return UUID.randomUUID().toString().replace("-", "");
    }
}
