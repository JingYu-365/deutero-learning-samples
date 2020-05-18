package me.jkong.hbase;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

/**
 * 此文件生成 {@link WriteDataToHBaseByBulkLoad} 所需要的文件
 *
 * @author JKong
 * @version v0.0.1
 * @date 2020/5/14 14:59.
 */
public class GenerateTestFile {
    public static void main(String[] args) {
        FileWriter writer = null;
        BufferedWriter bw = null;
        try {
            // 1. 准备数据
            StringBuilder sb = new StringBuilder();

            StringBuilder id;
            for (int i = 1; i <= 1000; i++) {
                id = new StringBuilder(i + "");
                while (true) {
                    if (id.length() != 4) {
                        id.insert(0, "0");
                    } else {
                        break;
                    }
                }
                sb.append("user_").append(id).append(",")
                        .append("user_").append(id).append("@126.com").append(",")
                        .append("1886666").append(id).append(",")
                        .append(i).append(",")
                        .append(i % 3 == 0 ? "female" : "man").append(",")
                        .append("username_").append(id).append("\n");
            }

            File userInfoFile = new File("./spark-hbase-sample/test.txt");
            writer = new FileWriter(userInfoFile);
            bw = new BufferedWriter(writer);
            bw.write(sb.toString());
            bw.flush();
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if (bw != null) {
                try {
                    bw.close();
                } catch (IOException e) {
                    // ignore
                }
            }

            if (writer != null) {
                try {
                    writer.close();
                } catch (IOException e) {
                    // ignore
                }
            }
        }
    }
}