package me.jkong.hbase;

import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.conf.Configured;
import org.apache.hadoop.fs.FileSystem;
import org.apache.hadoop.fs.Path;
import org.apache.hadoop.hbase.HBaseConfiguration;
import org.apache.hadoop.hbase.KeyValue;
import org.apache.hadoop.hbase.TableName;
import org.apache.hadoop.hbase.client.Connection;
import org.apache.hadoop.hbase.client.ConnectionFactory;
import org.apache.hadoop.hbase.client.Result;
import org.apache.hadoop.hbase.io.ImmutableBytesWritable;
import org.apache.hadoop.hbase.mapreduce.HFileOutputFormat2;
import org.apache.hadoop.hbase.mapreduce.TableOutputFormat;
import org.apache.hadoop.hbase.tool.BulkLoadHFiles;
import org.apache.hadoop.hbase.tool.BulkLoadHFilesTool;
import org.apache.hadoop.hbase.util.Bytes;
import org.apache.hadoop.mapreduce.Job;
import org.apache.hadoop.mapreduce.lib.input.FileInputFormat;
import org.apache.hadoop.mapreduce.lib.input.TextInputFormat;
import org.apache.hadoop.mapreduce.lib.output.FileOutputFormat;
import org.apache.hadoop.util.Tool;
import org.apache.spark.SparkConf;
import org.apache.spark.api.java.JavaPairRDD;
import org.apache.spark.api.java.JavaRDD;
import org.apache.spark.api.java.JavaSparkContext;
import org.apache.spark.api.java.function.PairFlatMapFunction;
import org.apache.spark.storage.StorageLevel;
import scala.Serializable;
import scala.Tuple2;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;


/**
 * Spark使用HFile批量将数据导入HBase
 *
 * @author JKong
 * @version v0.0.1
 * @date 2020/5/13 16:54.
 */
public class WriteDataToHBaseByBulkLoad extends Configured implements Tool, Serializable {
    private static final long serialVersionUID = 2L;

    private static final String HBASE_ZOOKEEPER_QUORUM = "10.10.27.47,10.10.27.48,10.10.27.49";
    private static final String HDFS_ADDR = "hdfs://10.10.27.47:8020";
    private static final String HDFS_INPUT_FILE_PATH = "hdfs://10.10.27.47:8020/jkong/test.txt";
    private static final String HDFS_OUTPUT_FILE_PATH = "hdfs://10.10.27.47:8020/jkong/hfile";
    private static final String TABLE_NAME = "user_table";
    private static final String COLUMN_FAMILY_INFO = "information";
    private static final String COLUMN_FAMILY_CONTACT = "contact";

    public static void main(String[] args) {
        try {
            args = new String[]{HDFS_INPUT_FILE_PATH, HDFS_OUTPUT_FILE_PATH};
            long start = System.currentTimeMillis();
            int run = new WriteDataToHBaseByBulkLoad().run(args);
            long end = System.currentTimeMillis();
            System.out.println("数据导入成功，总计耗时：" + (end - start) / 1000 + "s");
            System.exit(run);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public int run(String[] args) throws Exception {
        //初始化sparkContext
        SparkConf sparkConf = new SparkConf()
                .setAppName("BatchDataWriteIntoHBaseBySpark")
                .setMaster("local[*]")
                //指定序列化格式，默认是java序列化
                .set("spark.serializer", "org.apache.spark.serializer.KryoSerializer")
                //告知哪些类型需要序列化
                .registerKryoClasses(new Class[]{ImmutableBytesWritable.class, Result.class});

        boolean b = false;
        try (JavaSparkContext jsc = new JavaSparkContext(sparkConf)) {
            // 1. 设置HBase信息
            TableName tableName = TableName.valueOf(TABLE_NAME);
            Configuration configuration = HBaseConfiguration.create();
            configuration.set("hbase.zookeeper.quorum", HBASE_ZOOKEEPER_QUORUM);
            configuration.set("hbase.zookeeper.property.clientPort", "2181");
            configuration.set("hbase.bulkload.retries.number", "10");
            configuration.set("fs.defaultFS", HDFS_ADDR);
            configuration.set("dfs.socket.timeout", "180000");

            configuration.set(TableOutputFormat.OUTPUT_TABLE, tableName.toString());


            // 2. 设置Job信息
            String jobName = "Spark Bulk Loading HBase Table:" + tableName.getNameAsString();
            Job job = Job.getInstance(configuration, jobName);
            job.setInputFormatClass(TextInputFormat.class);
            job.setMapOutputKeyClass(ImmutableBytesWritable.class);
            job.setMapOutputValueClass(KeyValue.class);
            job.setOutputFormatClass(HFileOutputFormat2.class);


            // 3. HDFS 操作配置
            String inputPath = args[0];
            String outputPath = args[1];
            // 3.1 输入路径
            FileInputFormat.addInputPaths(job, inputPath);
            FileSystem fs = FileSystem.get(configuration);
            Path output = new Path(outputPath);
            if (fs.exists(output)) {
                //如果输出路径存在，就将其删除
                fs.delete(output, true);
            }
            fs.close();
            // 3.2 HFile 输出路径
            FileOutputFormat.setOutputPath(job, output);


            // 4. 使用Spark从文件中读取数据文件
            JavaRDD<String> lines = jsc.textFile(inputPath);
            lines.persist(StorageLevel.MEMORY_AND_DISK_SER());
            JavaPairRDD<ImmutableBytesWritable, KeyValue> hfileRdd =
                    lines.flatMapToPair(new PairFlatMapFunction<String, ImmutableBytesWritable, KeyValue>() {
                        private static final long serialVersionUID = 1L;

                        @Override
                        public Iterator<Tuple2<ImmutableBytesWritable, KeyValue>> call(String text) throws Exception {
                            List<Tuple2<ImmutableBytesWritable, KeyValue>> tps = new ArrayList<>();
                            if (null == text || text.length() < 1) {
                                //不能返回null
                                return tps.iterator();
                            }
                            // 处理文件数据
                            String[] resArr = text.split(",");
                            if (resArr.length == 6) {
                                byte[] rowKeyByte = Bytes.toBytes(resArr[0]);
                                byte[] familyNamedInfo = Bytes.toBytes(COLUMN_FAMILY_INFO);
                                byte[] familyNamedContact = Bytes.toBytes(COLUMN_FAMILY_CONTACT);
                                ImmutableBytesWritable ibw = new ImmutableBytesWritable(rowKeyByte);
                                //EP,HP,LP,MK,MT,SC,SN,SP,ST,SY,TD,TM,TQ,UX（字典顺序排序）
                                //注意，这地方rowkey、列族和列都要按照字典排序，如果有多个列族，也要按照字典排序，rowkey排序我们交给spark的sortByKey去管理
                                // TODO: 2020/5/14 如果不按照字典排序会怎么样？
                                tps.add(new Tuple2<>(ibw, new KeyValue(rowKeyByte, familyNamedContact, Bytes.toBytes("email"), Bytes.toBytes(resArr[1]))));
                                tps.add(new Tuple2<>(ibw, new KeyValue(rowKeyByte, familyNamedContact, Bytes.toBytes("phone"), Bytes.toBytes(resArr[2]))));
                                tps.add(new Tuple2<>(ibw, new KeyValue(rowKeyByte, familyNamedInfo, Bytes.toBytes("age"), Bytes.toBytes(resArr[3]))));
                                tps.add(new Tuple2<>(ibw, new KeyValue(rowKeyByte, familyNamedInfo, Bytes.toBytes("gender"), Bytes.toBytes(resArr[4]))));
                                tps.add(new Tuple2<>(ibw, new KeyValue(rowKeyByte, familyNamedInfo, Bytes.toBytes("username"), Bytes.toBytes(resArr[5]))));
                            }
                            return tps.iterator();
                        }
                    }).sortByKey();

            Connection connection = ConnectionFactory.createConnection(configuration);
            HFileOutputFormat2.configureIncrementalLoad(job, connection.getTable(tableName), connection.getRegionLocator(tableName));

            //生成 HFile 文件
            hfileRdd.saveAsNewAPIHadoopFile(outputPath, ImmutableBytesWritable.class, KeyValue.class, HFileOutputFormat2.class, job.getConfiguration());

            // bulk load start
//            Table table = connection.getTable(tableName);
//            Admin admin = connection.getAdmin();
//            LoadIncrementalHFiles load = new LoadIncrementalHFiles(configuration);
//            load.doBulkLoad(new Path(outputPath), admin, table, connection.getRegionLocator(tableName));

            BulkLoadHFiles loadFiles = new BulkLoadHFilesTool(configuration);
            loadFiles.bulkLoad(tableName, output);

            // 等待任务的完成
            b = job.waitForCompletion(true);
        } catch (Exception e) {
            e.printStackTrace();
        }

        return b ? 0 : 1;
    }

}