package me.jkong.hbase;

import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.hbase.HBaseConfiguration;
import org.apache.hadoop.hbase.client.Result;
import org.apache.hadoop.hbase.client.Scan;
import org.apache.hadoop.hbase.io.ImmutableBytesWritable;
import org.apache.hadoop.hbase.mapreduce.TableInputFormat;
import org.apache.hadoop.hbase.protobuf.ProtobufUtil;
import org.apache.hadoop.hbase.protobuf.generated.ClientProtos;
import org.apache.hadoop.hbase.util.Base64;
import org.apache.hadoop.hbase.util.Bytes;
import org.apache.spark.SparkConf;
import org.apache.spark.api.java.JavaPairRDD;
import org.apache.spark.api.java.JavaRDD;
import org.apache.spark.api.java.JavaSparkContext;
import org.apache.spark.api.java.function.Function;
import org.apache.spark.sql.Dataset;
import org.apache.spark.sql.Row;
import org.apache.spark.sql.RowFactory;
import org.apache.spark.sql.SQLContext;
import org.apache.spark.sql.types.DataTypes;
import org.apache.spark.sql.types.StructField;
import org.apache.spark.sql.types.StructType;
import scala.Tuple2;

import java.util.Arrays;
import java.util.List;

/**
 * 使用Spark 读取 HBase中数据，并使用DataFrame处理数据
 *
 * @author JKong
 * @version v0.0.1
 * @date 2020/5/6 15:30.
 */
public class HBaseDataSourceReadDataBySpark {
    public static void main(String[] args) {
        //初始化SparkContext
        SparkConf sparkConf = new SparkConf().setAppName("HBaseDataSourceReadDataBySpark").setMaster("local");
        JavaSparkContext jsc = new JavaSparkContext(sparkConf);
        SQLContext sqlContext = new SQLContext(jsc);
        Configuration conf = HBaseConfiguration.create();

        Scan scan = new Scan();
        scan.setStartRow(Bytes.toBytes("user-003"));


        try {
            //将scan编码
            ClientProtos.Scan proto = ProtobufUtil.toScan(scan);
            String scanToString = Base64.encodeBytes(proto.toByteArray());
            //表名
            String tableName = "user_table";
            conf.set(TableInputFormat.INPUT_TABLE, tableName);
            conf.set(TableInputFormat.SCAN, scanToString);
            //ZooKeeper集群
            conf.set("hbase.zookeeper.quorum", "10.10.27.47,10.10.27.48,10.10.27.49");
            conf.set("hbase.zookeeper.property.clientPort", "2181");

            //将HBase数据转成RDD
            JavaPairRDD<ImmutableBytesWritable, Result> hBaseRdd =
                    jsc.newAPIHadoopRDD(conf, TableInputFormat.class, ImmutableBytesWritable.class, Result.class);
            //再将以上结果转成Row类型RDD
            JavaRDD<Row> hBaseRow = hBaseRdd.map(new Function<Tuple2<ImmutableBytesWritable, Result>, Row>() {
                private static final long serialVersionUID = 1L;

                @Override
                public Row call(Tuple2<ImmutableBytesWritable, Result> tuple2) {
                    Result result = tuple2._2;
                    String rowKey = Bytes.toString(result.getRow());
                    String name = Bytes.toString(tuple2._2.getValue("information".getBytes(), "username".getBytes()));
                    String ageStr = Bytes.toString(tuple2._2.getValue("information".getBytes(), "age".getBytes()));
                    int age = Integer.parseInt(ageStr);
                    String gender = Bytes.toString(tuple2._2.getValue("information".getBytes(), "gender".getBytes()));

                    String tag = Bytes.toString(tuple2._2.getValue("contact".getBytes(), "email".getBytes()));
                    String level = Bytes.toString(tuple2._2.getValue("contact".getBytes(), "phone".getBytes()));

                    return RowFactory.create(rowKey, name, age, gender, tag, level);
                }
            });

            //顺序必须与构建RowRDD的顺序一致
            List<StructField> structFields = Arrays.asList(
                    DataTypes.createStructField("rowKey", DataTypes.StringType, true),
                    DataTypes.createStructField("username", DataTypes.StringType, true),
                    DataTypes.createStructField("age", DataTypes.IntegerType, true),
                    DataTypes.createStructField("gender", DataTypes.StringType, true),
                    DataTypes.createStructField("email", DataTypes.StringType, true),
                    DataTypes.createStructField("phone", DataTypes.StringType, true)
            );
            //构建schema
            StructType schema = DataTypes.createStructType(structFields);
            //生成DataFrame
            Dataset<Row> dataFrame = sqlContext.createDataFrame(hBaseRow, schema);
            //df相关操作
            dataFrame.printSchema();
            dataFrame.show();
            dataFrame.createOrReplaceTempView("user");
            Dataset<Row> newDataFrame = sqlContext.sql("select rowKey as id,username,email,phone from user");
            newDataFrame.show();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            jsc.close();
        }
    }
}