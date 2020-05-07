package me.jkong.hbase;

import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.hbase.Cell;
import org.apache.hadoop.hbase.CellUtil;
import org.apache.hadoop.hbase.HBaseConfiguration;
import org.apache.hadoop.hbase.TableName;
import org.apache.hadoop.hbase.client.Result;
import org.apache.hadoop.hbase.client.Scan;
import org.apache.hadoop.hbase.io.ImmutableBytesWritable;
import org.apache.hadoop.hbase.mapreduce.TableInputFormat;
import org.apache.hadoop.hbase.spark.JavaHBaseContext;
import org.apache.hadoop.hbase.util.Bytes;
import org.apache.spark.SparkConf;
import org.apache.spark.api.java.JavaRDD;
import org.apache.spark.api.java.JavaSparkContext;
import scala.Tuple2;

import java.util.List;

/**
 * 将HBase作为数据源
 *
 * @author JKong
 * @version v0.0.1
 * @date 2020/4/30 9:47.
 */
public class DataSourceByHBase {
    public static void main(String[] args) {

        // 需要在环境变量中设置:HADOOP_USER_NAME : hbase，否则会出现权限问题
        System.setProperty("HADOOP_USER_NAME", "hbase");
        // 不设置会抛异常： Task 0.0 in stage 0.0 (TID 0) had a not serializable result: org.apache.hadoop.hbase.io.ImmutableBytesWritable
        System.setProperty("spark.serializer", "org.apache.spark.serializer.KryoSerializer");

        String tableName = "student";
        SparkConf sparkConf = new SparkConf().setAppName("SparkDataFromHBase").setMaster("local");
        JavaSparkContext jsc = new JavaSparkContext(sparkConf);

        try {
            Configuration hconf = HBaseConfiguration.create();
            hconf.set("hbase.zookeeper.quorum", "10.10.32.17,10.10.32.18,10.10.32.19");
            hconf.set("hbase.zookeeper.property.clientPort", "2181");
            hconf.set(TableInputFormat.INPUT_TABLE, tableName);

            JavaHBaseContext hbaseContext = new JavaHBaseContext(jsc, hconf);

            Scan scan = new Scan();
            JavaRDD<Tuple2<ImmutableBytesWritable, Result>> hBaseRDD = hbaseContext.hbaseRDD(TableName.valueOf(tableName), scan);
            List<Tuple2<ImmutableBytesWritable, Result>> t = hBaseRDD.take(10);
            for (Tuple2 tuple2 : t) {
                ImmutableBytesWritable row = (ImmutableBytesWritable) tuple2._1;
                System.out.println(Bytes.toString(row.get()));
                Result result = (Result) tuple2._2();
                List<Cell> cells = result.listCells();
                System.out.println(Bytes.toString(CellUtil.cloneRow(cells.get(0))));
                for (Cell cell : cells) {
                    System.out.println(Bytes.toString(CellUtil.cloneValue(cell)));
                }
            }
        } finally {
            jsc.stop();
        }
    }
}