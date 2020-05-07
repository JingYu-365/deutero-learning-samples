package me.jkong.sql.sparksession;

import org.apache.spark.SparkConf;
import org.apache.spark.sql.Dataset;
import org.apache.spark.sql.Row;
import org.apache.spark.sql.SparkSession;

/**
 * 使用DataFrame操作数据
 *
 * @author JKong
 * @version v0.0.1
 * @date 2020/4/13 15:02.
 */
public class DataFrameOperationExample {

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
//        df.printSchema();

        // 5. 仅查询 name 列数据
//        df.select("name").show();

        // 6. 查询所有人信息，并将 age+1 展示
//        df.select(df.col("name"), df.col("age").plus(1)).show();

        // 7. 根据 age 分组统计人数总和
//        df.groupBy("age").count().show();

        // 8. 过滤出age>27的人的信息
//        df.filter(df.col("age").$greater$eq(27)).show();

        // 9. 使用SQL查询数据
        // 注意：如果不创建视图将会报错：Table or view not found: people;
//        df.createOrReplaceTempView("people");
//        Dataset<Row> sqlDF = sparkSession.sql("SELECT name FROM people WHERE age > 27");
//        sqlDF.show();

        // 10. Spark SQL中的临时视图是会话范围的，如果创建它的会话终止，它将消失。
        // 如果想要多个session共享一份视图数据，可以创建全局视图
//        df.createOrReplaceGlobalTempView("people");
        // 注意：如果使用全局session共享view，那么在查询时必须要使用 global_temp.XXXX
//        sparkSession.sql("SELECT * FROM global_temp.people").show();
//        sparkSession.newSession().sql("SELECT * FROM global_temp.people").show();

        sparkSession.stop();
    }
}