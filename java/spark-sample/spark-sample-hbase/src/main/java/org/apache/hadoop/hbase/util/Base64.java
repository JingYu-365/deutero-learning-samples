package org.apache.hadoop.hbase.util;


/**
 * TODO
 *
 * @author JKong
 * @version v0.0.1
 * @date 2020/5/6 13:04.
 */
public class Base64 {

    public static String encodeBytes(byte[] data) {
        return java.util.Base64.getEncoder()
                .encodeToString(data);

    }

    public static byte[] decode(String data) {
        return java.util.Base64.getDecoder().decode(data);
    }
}