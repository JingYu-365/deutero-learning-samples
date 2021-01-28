package me.jkong.sql;

import java.io.*;
import java.nio.charset.StandardCharsets;

/**
 * TODO
 *
 * @author JKong
 * @version v0.0.1
 * @date 2020/10/27 15:55.
 */
public class FilePathTest {
    public static void main(String[] args) {
        try {

            String path = FilePathTest.class.getResource("/hdfs/test.txt").getPath();
            System.out.println(path);
            System.out.println(new File(path).exists());
            // 获取路径
            InputStream resourceAsStream = FilePathTest.class.getResourceAsStream("/hdfs/test.txt");
            InputStreamReader isr = new InputStreamReader(resourceAsStream, StandardCharsets.UTF_8);
            BufferedReader br = new BufferedReader(isr);
            String lineTxt;
            while ((lineTxt = br.readLine()) != null) {
                System.out.println(lineTxt);
            }
            br.close();
        } catch (Exception e) {
            e.printStackTrace();
        }

    }
}