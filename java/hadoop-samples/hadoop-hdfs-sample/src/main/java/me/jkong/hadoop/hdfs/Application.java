package me.jkong.hadoop.hdfs;

import me.jkong.hadoop.hdfs.config.Hdfs;
import org.apache.hadoop.fs.FileSystem;

/**
 * @author JKong
 * @version v1.0
 * @description 主启动类
 * @date 2019/10/12 11:31.
 */
public class Application {

    public static void main(String[] args) {
        FileSystem fileSystem = Hdfs.getFileSystemInstance();
        System.out.println(fileSystem);
    }
}