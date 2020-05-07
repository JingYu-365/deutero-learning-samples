package me.jkong.sql.creation;

import org.apache.spark.SparkConf;
import org.apache.spark.sql.Dataset;
import org.apache.spark.sql.Row;
import org.apache.spark.sql.SparkSession;

/**
 * 数据从json中获取
 *
 * @author JKong
 * @version v0.0.1
 * @date 2020/4/13 14:13.
 */
public class DataFromLocalFile {

    public static void main(String[] args) {
        // 创建 SparkSession 对象
        SparkSession sparkSession = SparkSession.builder()
                .appName("spark sql demo")
                .config(new SparkConf().setMaster("local"))
                .getOrCreate();

        // 2. 从json文件中读取数据
        // 注意：因为sprak在读取数据时将一行数据作为一条记录，因此不能将json数据进行格式化，不可以将一个json对象写成多行，如下：
        // {"name":"Michael"}
        // {"name":"Andy", "age":30}
        // {"name":"Justin", "age":19}
        Dataset<Row> df = sparkSession.read().json("src/main/resources/people.json");

        // 3. 控制台打印数据
        df.show();

        // 4. 控制台输出数据schema
        df.printSchema();

        sparkSession.stop();
    }
}