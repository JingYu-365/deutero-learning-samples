package me.jkong.hbase;

import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.hbase.HBaseConfiguration;
import org.apache.hadoop.hbase.spark.HBaseContext;
import org.apache.hadoop.hbase.spark.datasources.HBaseTableCatalog;
import org.apache.spark.SparkConf;
import org.apache.spark.api.java.JavaSparkContext;
import org.apache.spark.sql.Dataset;
import org.apache.spark.sql.Row;
import org.apache.spark.sql.SparkSession;

/**
 * TODO
 *
 * @author JKong
 * @version v0.0.1
 * @date 2020/5/6 15:30.
 */
public class DataSourceReadDataByHBase {
    public static void main(String[] args) {
        SparkConf sparkConf = new SparkConf().setAppName("DataSourceReadDataByHBase").setMaster("local");
        JavaSparkContext jsc = new JavaSparkContext(sparkConf);
        SparkSession sparkSession = SparkSession.builder()
                .sparkContext(jsc.sc())
                .getOrCreate();

        //设置要访问的hbase的zookeeper
        Configuration configuration = HBaseConfiguration.create();
        configuration.set("hbase.zookeeper.quorum", "10.10.32.17,10.10.32.18,10.10.32.19");
        configuration.set("hbase.zookeeper.property.clientPort", "2181");
        HBaseContext hBaseContext = new HBaseContext(jsc.sc(), configuration, null);

        //读取数据
        String tableName = "student";
        Dataset<Row> df = sparkSession.read()
                .format("org.apache.hadoop.hbase.spark")
                .option(HBaseTableCatalog.tableCatalog(), tableName)
                .load();
        System.out.println("read result: ");
        df.show();

        df.registerTempTable("student");
        sparkSession.sql("select count(name) from student").show();
    }
}