package me.jkong.hbase;

import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.hbase.Cell;
import org.apache.hadoop.hbase.CellUtil;
import org.apache.hadoop.hbase.HBaseConfiguration;
import org.apache.hadoop.hbase.TableName;
import org.apache.hadoop.hbase.client.Result;
import org.apache.hadoop.hbase.client.Scan;
import org.apache.hadoop.hbase.filter.Filter;
import org.apache.hadoop.hbase.filter.PageFilter;
import org.apache.hadoop.hbase.io.ImmutableBytesWritable;
import org.apache.hadoop.hbase.mapreduce.TableInputFormat;
import org.apache.hadoop.hbase.spark.JavaHBaseContext;
import org.apache.hadoop.hbase.util.Bytes;
import org.apache.spark.SparkConf;
import org.apache.spark.api.java.JavaRDD;
import org.apache.spark.api.java.JavaSparkContext;
import scala.Tuple2;

import java.util.ArrayList;
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

        // 采坑1：需要在环境变量中设置:HADOOP_USER_NAME : hbase，否则会出现权限问题
        System.setProperty("HADOOP_USER_NAME", "hbase");
        // 采坑2：不设置会抛异常： Task 0.0 in stage 0.0 (TID 0) had a not serializable result: org.apache.hadoop.hbase.io.ImmutableBytesWritable
        System.setProperty("spark.serializer", "org.apache.spark.serializer.KryoSerializer");

        SparkConf sparkConf = new SparkConf().setAppName("SparkDataFromHBase").setMaster("local");
        JavaSparkContext jsc = new JavaSparkContext(sparkConf);

        try {
            Configuration hconf = HBaseConfiguration.create();
            hconf.set("hbase.zookeeper.quorum", "10.10.27.47,10.10.27.48,10.10.27.49");
            hconf.set("hbase.zookeeper.property.clientPort", "2181");
            String tableName = "user_table";
            hconf.set(TableInputFormat.INPUT_TABLE, tableName);
            JavaHBaseContext hBaseContext = new JavaHBaseContext(jsc, hconf);


            // 1. 获取指定family例族的所有例数据
            Scan scan = getSpecialColumnFamiliesData("information", "contact");


            // 2. 指定family:column格式获取具体列数据
            // 注意：如果列不存在则获取不到任何数据
            List<String> list = new ArrayList<>();
            list.add("contact:email");
            list.add("contact:phone");
            list.add("information:username");
            list.add("contact:gender");
            scan = getSpecialFamilyAndColumnsData(list);


            // 3. 获取指定 rowkey 区间的数据
            scan = getDataBetweenRowKey("user-003", "user-005");


            scan = getRowDataByPageSize(2);

            scan = getRowDataByVersion(5);


            JavaRDD<Tuple2<ImmutableBytesWritable, Result>> hBaseRDD = hBaseContext.hbaseRDD(TableName.valueOf(tableName), scan);
            List<Tuple2<ImmutableBytesWritable, Result>> t = hBaseRDD.take(10);

            for (Tuple2 tuple2 : t) {
                ImmutableBytesWritable row = (ImmutableBytesWritable) tuple2._1;
//                System.out.println(Bytes.toString(row.get()));
                Result result = (Result) tuple2._2();
                List<Cell> cells = result.listCells();
                System.out.println(Bytes.toString(CellUtil.cloneRow(cells.get(0))));
                for (Cell cell : cells) {
                    System.out.println(Bytes.toString(CellUtil.cloneValue(cell)));
                }
                System.out.println("------------------------------");
            }
        } finally {
            jsc.stop();
        }
    }

    private static Scan getRowDataByVersion(int version) {
        Scan scan = new Scan();

//        scan.readAllVersions();
        scan.readVersions(version);

        return scan;
    }


    private static Scan getRowDataByPageSize(int size) {
        Scan scan = new Scan();
        Filter filter = new PageFilter(size);
        scan.setFilter(filter);

        // 每个family跳过几个column查询
//        scan.setRowOffsetPerColumnFamily(2);
        return scan;
    }


    /**
     * 获取指定rowkey范围内的数据
     *
     * @param start 开始 rowkey
     * @param end   结束 rowkey
     * @return 扫描器
     */
    private static Scan getDataBetweenRowKey(String start, String end) {
        Scan scan = new Scan();
        scan.setStartRow(Bytes.toBytes(start));
        scan.setStopRow(Bytes.toBytes(end));
        return scan;
    }


    /**
     * 指定 familyName:columnName
     *
     * @param columns 格式 List<familyName:columnName>
     * @return 扫描器
     */
    private static Scan getSpecialFamilyAndColumnsData(List<String> columns) {

        Scan scan = new Scan();
        for (String column : columns) {
            String[] familyAndColumn = column.split(":");
            scan.addColumn(Bytes.toBytes(familyAndColumn[0]), Bytes.toBytes(familyAndColumn[1]));
        }
        return scan;
    }


    /**
     * 指定例族，获取此例族的数据
     *
     * @param familyName 例族的名称
     * @return 扫描器
     */
    public static Scan getSpecialColumnFamiliesData(String... familyName) {
        Scan scan = new Scan();
        for (String name : familyName) {
            scan.addFamily(Bytes.toBytes(name));
        }
        return scan;
    }

/*
    System.out.println("行键: "+new String(rowArray,cell.getRowOffset(),cell.getRowLength()));
    System.out.println("列族名: "+new String(familyArray,cell.getFamilyOffset(),cell.getFamilyLength()));
    System.out.println("列名: "+new String(qualifierArray,cell.getQualifierOffset(),cell.getQualifierLength()));
    System.out.println("value: "+new String(valueArray,cell.getValueOffset(),cell.getValueLength()));
 */
}