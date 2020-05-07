package me.jkong.hbase;

import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.hbase.HBaseConfiguration;
import org.apache.hadoop.hbase.spark.HBaseContext;
import org.apache.hadoop.hbase.spark.datasources.HBaseTableCatalog;
import org.apache.spark.SparkConf;
import org.apache.spark.api.java.JavaRDD;
import org.apache.spark.api.java.JavaSparkContext;
import org.apache.spark.sql.Dataset;
import org.apache.spark.sql.Row;
import org.apache.spark.sql.SaveMode;
import org.apache.spark.sql.SparkSession;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 * 使用sparksql操作Hbase
 *
 * @author JKong
 * @version v0.0.1
 * @date 2020/5/6 14:30.
 */
public class SparkSqlDataSourceByHBase {
    public static void main(String[] args) {
        SparkConf sparkConf = new SparkConf().setAppName("SparkSqlDataSourceByHBase").setMaster("local");
        JavaSparkContext jsc = new JavaSparkContext(sparkConf);
        SparkSession sparkSession = SparkSession.builder()
                .sparkContext(jsc.sc())
                .getOrCreate();

        //设置要访问的hbase的zookeeper
        Configuration configuration = HBaseConfiguration.create();
        configuration.set("hbase.zookeeper.quorum", "10.10.32.17,10.10.32.18,10.10.32.19");
        configuration.set("hbase.zookeeper.property.clientPort", "2181");
        //一创建hbaseContext, 写入时会用到
        HBaseContext hBaseContext = new HBaseContext(jsc.sc(), configuration, null);

        //创建一个测试用的RDD
        List<Integer> data = new ArrayList<>();
        for (int i = 0; i < 256; i++) {
            data.add(i);
        }
        JavaRDD<Integer> rdd = jsc.parallelize(data);
        JavaRDD<HBaseRecord> rdd1 = rdd.map(i -> new HBaseRecord(i, "extra"));
        rdd1.collect().forEach(System.out::println);

        //根据RDD创建数据帧
        Dataset<Row> df = sparkSession.createDataFrame(rdd1, HBaseRecord.class);

        //定义映射的catalog
        String catalog = "{" +
                "       \"table\":{\"namespace\":\"default\", \"name\":\"table1\"}," +
                "       \"rowkey\":\"key\"," +
                "       \"columns\":{" +
                "         \"col0\":{\"cf\":\"rowkey\", \"col\":\"key\", \"type\":\"string\"}," +
                "         \"col1\":{\"cf\":\"cf1\", \"col\":\"col1\", \"type\":\"boolean\"}," +
                "         \"col2\":{\"cf\":\"cf2\", \"col\":\"col2\", \"type\":\"double\"}," +
                "         \"col3\":{\"cf\":\"cf3\", \"col\":\"col3\", \"type\":\"float\"}," +
                "         \"col4\":{\"cf\":\"cf4\", \"col\":\"col4\", \"type\":\"int\"}," +
                "         \"col5\":{\"cf\":\"cf5\", \"col\":\"col5\", \"type\":\"bigint\"}," +
                "         \"col6\":{\"cf\":\"cf6\", \"col\":\"col6\", \"type\":\"smallint\"}," +
                "         \"col7\":{\"cf\":\"cf7\", \"col\":\"col7\", \"type\":\"string\"}," +
                "         \"col8\":{\"cf\":\"cf8\", \"col\":\"col8\", \"type\":\"tinyint\"}" +
                "       }" +
                "     }";

        //写入数据
        df.write()
                .format("org.apache.hadoop.hbase.spark")
                .option(HBaseTableCatalog.tableCatalog(), catalog)
                //写入到3个分区
                .option(HBaseTableCatalog.newTable(), "3")
                // 覆盖模式
                .mode(SaveMode.Overwrite)
                .save();

        System.out.println("--------------------------");

        //读取数据
        Dataset<Row> df2 = sparkSession.read()
                .format("org.apache.hadoop.hbase.spark")
                .option(HBaseTableCatalog.tableCatalog(), catalog)
                .load();
        System.out.println("read result: ");
        df2.show();
    }

    /**
     * 类需要可序列化
     */
    static class HBaseRecord implements Serializable {
        private static final long serialVersionUID = 4331526295356820188L;
        //属性一定要getter/setter, 即使是public
        public String col0;
        public Boolean col1;
        public Double col2;
        public Float col3;
        public Integer col4;
        public Long col5;
        public Short col6;
        public String col7;
        public Byte col8;

        public String getCol0() {
            return col0;
        }

        public void setCol0(String col0) {
            this.col0 = col0;
        }

        public Boolean getCol1() {
            return col1;
        }

        public void setCol1(Boolean col1) {
            this.col1 = col1;
        }

        public Double getCol2() {
            return col2;
        }

        public void setCol2(Double col2) {
            this.col2 = col2;
        }

        public Float getCol3() {
            return col3;
        }

        public void setCol3(Float col3) {
            this.col3 = col3;
        }

        public Integer getCol4() {
            return col4;
        }

        public void setCol4(Integer col4) {
            this.col4 = col4;
        }

        public Long getCol5() {
            return col5;
        }

        public void setCol5(Long col5) {
            this.col5 = col5;
        }

        public Short getCol6() {
            return col6;
        }

        public void setCol6(Short col6) {
            this.col6 = col6;
        }

        public String getCol7() {
            return col7;
        }

        public void setCol7(String col7) {
            this.col7 = col7;
        }

        public Byte getCol8() {
            return col8;
        }

        public void setCol8(Byte col8) {
            this.col8 = col8;
        }

        public HBaseRecord(Integer i, String s) {
            col0 = String.format("row%03d", i);
            col1 = i % 2 == 0;
            col2 = Double.valueOf(i);
            col3 = Float.valueOf(i);
            col4 = i;
            col5 = Long.valueOf(i);
            col6 = i.shortValue();
            col7 = "String:" + s;
            col8 = i.byteValue();
        }

        @Override
        public String toString() {
            return "HBaseRecord{" +
                    "col0='" + col0 + '\'' +
                    ", col1=" + col1 +
                    ", col2=" + col2 +
                    ", col3=" + col3 +
                    ", col4=" + col4 +
                    ", col5=" + col5 +
                    ", col6=" + col6 +
                    ", col7='" + col7 + '\'' +
                    ", col8=" + col8 +
                    '}';
        }
    }
}