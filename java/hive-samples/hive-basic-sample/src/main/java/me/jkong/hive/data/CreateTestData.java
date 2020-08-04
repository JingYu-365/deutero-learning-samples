package me.jkong.hive.data;

import java.io.*;
import java.util.Random;
import java.util.UUID;

/**
 * 生产数据用于测试
 *
 * @author JKong
 * @version v0.0.1
 * @date 2020/7/15 11:03.
 */
public class CreateTestData {
    public static void main(String[] args) throws IOException {
        File out = new File("H:/test_data/2000000000.txt");
        if (!out.exists()) {
            out.createNewFile();
        }

        // 以追加的方式将数据写出
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(out, true))) {

            int num = 0;
            Random random = new Random();
            while (++num < 2000000000) {
                String builder = num +
                        "," +
                        UUID.randomUUID().toString() +
                        "," +
                        random.nextInt(100) +
                        "," +
                        (num % 3 == 0) +
                        "," +
                        System.currentTimeMillis() +
                        "\n";
                writer.write(builder);

                if (num % 10000000 == 0) {
                    System.out.println(num);
                }
                if (num % 100000000 == 0) {
                    System.out.println(num / 100000000 + " 亿条数据！");
                }
            }
            writer.flush();
        }
    }
}