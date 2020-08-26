package me.jkong.hive.data;

import java.io.DataInput;
import java.io.DataOutput;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.sql.Timestamp;

import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.fs.FileSystem;
import org.apache.hadoop.fs.Path;
import org.apache.hadoop.hive.ql.exec.vector.*;
import org.apache.hadoop.hive.ql.io.orc.CompressionKind;
import org.apache.hadoop.hive.ql.io.orc.OrcFile;
import org.apache.hadoop.hive.ql.io.orc.Writer;
import org.apache.orc.TypeDescription;


/**
 * 生成 ORC 格式文件到 HDFS 中
 *
 * @author JKong
 * @version v0.0.1
 * @date 2020/7/21 16:08.
 */
public class WriteOrcFileToHDFS {

    static String ClusterName = "nsstargate";
    private static final String HADOOP_URL = "hdfs://" + ClusterName;
    public static Configuration conf;

    static {
        conf = new Configuration();
        conf.set("fs.defaultFS", HADOOP_URL);
        conf.set("dfs.nameservices", ClusterName);
        conf.set("dfs.ha.namenodes." + ClusterName, "nn1,nn2");
        conf.set("dfs.namenode.rpc-address." + ClusterName + ".nn1", "172.16.50.24:8020");
        conf.set("dfs.namenode.rpc-address." + ClusterName + ".nn2", "172.16.50.21:8020");
        conf.set("dfs.client.failover.proxy.provider." + ClusterName,
                "org.apache.hadoop.hdfs.server.namenode.ha.ConfiguredFailoverProxyProvider");
        conf.set("fs.hdfs.impl", "org.apache.hadoop.hdfs.DistributedFileSystem");
    }


    public static void main(String[] args) throws Exception {
        TypeDescription schema = TypeDescription.createStruct()
                .addField("id", TypeDescription.createLong())
                .addField("name", TypeDescription.createString())
                .addField("age", TypeDescription.createInt())
                .addField("gender", TypeDescription.createBoolean())
                .addField("create_time", TypeDescription.createLong());

        Path path = new Path("/a_jkong_test_data/orc/1000.orc");
        FileSystem fs;
        try {
            fs = path.getFileSystem(conf);
            if (fs.exists(path)) {
                fs.delete(path, true);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        Writer writer = OrcFile.createWriter(path,
                OrcFile.writerOptions(conf)
                        .setSchema(schema)
                        // 64 MB
                        .stripeSize(67108864)
                        // 128 KB
                        .bufferSize(131072)
                        // 128 MB
                        .blockSize(134217728)
                        .compress(CompressionKind.ZLIB)
                        .version(OrcFile.Version.V_0_12));
        //要写入的内容
        Object[][] contents = new Object[][]{
                {1l, 1.1, false, "2016-10-21 14:56:25", "abcd"},
                {2l, 1.2, true, "2016-10-22 14:56:25", "中文"}};

        VectorizedRowBatch batch = schema.createRowBatch();
        for (Object[] content : contents) {
            int rowCount = batch.size++;
            ((LongColumnVector) batch.cols[0]).vector[rowCount] = (long) content[0];
            ((DoubleColumnVector) batch.cols[1]).vector[rowCount] = (double) content[1];
            ((LongColumnVector) batch.cols[2]).vector[rowCount] = content[2].equals(true) ? 1 : 0;
            ((TimestampColumnVector) batch.cols[3]).time[rowCount] = (Timestamp.valueOf((String) content[3])).getTime();
            ((BytesColumnVector) batch.cols[4]).setVal(rowCount, content[4].toString().getBytes(StandardCharsets.UTF_8));

            //batch full
            if (batch.size == batch.getMaxSize()) {
                writer.addRowBatch(batch);
                batch.reset();
            }
        }
        if (batch.size > 0) {
            writer.addRowBatch(batch);
        }
        writer.close();
    }

}