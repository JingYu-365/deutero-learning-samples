package me.jkong.hadoop.hdfs.service;

import com.alibaba.fastjson.JSONObject;
import me.jkong.hadoop.hdfs.entity.HdfsFolder;
import org.junit.Test;

/**
 * @author JKong
 * @version v1.0
 * @description hdfs 操作测试类
 * @date 2019/10/12 11:59.
 */
public class HdfsOperationServiceTest {

    @Test
    public void mkDir() {
        HdfsOperationService.mkDir("/jkong/test");
    }


    @Test
    public void listFiles() {
        HdfsFolder hdfsFolder = HdfsOperationService.listFileFolders("/");
        System.out.println(JSONObject.toJSONString(hdfsFolder));
    }
}