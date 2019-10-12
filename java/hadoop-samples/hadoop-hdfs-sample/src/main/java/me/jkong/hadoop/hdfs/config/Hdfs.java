package me.jkong.hadoop.hdfs.config;

import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.fs.FileSystem;

import java.net.URI;

/**
 * @author JKong
 * @version v1.0
 * @description HDFS 配置
 * @date 2019/10/12 11:42.
 */
public class Hdfs {

    public static final String HDFS_PATH = "hdfs://hdfs.host:8020";

    /**
     * 获取文件系统实例
     * @return FileSystem
     */
    public static FileSystem getFileSystemInstance() {
        Configuration configuration = new Configuration();
        FileSystem fileSystem;
        try {
            fileSystem = FileSystem.get(new URI(HDFS_PATH), configuration, "hdfs");
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
        return fileSystem;
    }

}