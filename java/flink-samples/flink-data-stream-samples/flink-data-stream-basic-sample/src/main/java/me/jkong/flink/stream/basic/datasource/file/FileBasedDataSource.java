package me.jkong.flink.stream.basic.datasource.file;

import org.apache.flink.streaming.api.datastream.DataStreamSource;
import org.apache.flink.streaming.api.environment.LocalStreamEnvironment;
import org.apache.flink.streaming.api.environment.StreamExecutionEnvironment;

/**
 * 通过文件创建数据源
 *
 * @author JKong
 * @version v0.0.1
 * @date 2020/8/25 21:22.
 */
public class FileBasedDataSource {

    public static void main(String[] args) throws Exception {
        LocalStreamEnvironment env = StreamExecutionEnvironment.createLocalEnvironment();
        // 从本地文件系统读
        DataStreamSource<String> localLines = env.readTextFile("file:///path/to/my/textfile");

        // 读取HDFS文件
        DataStreamSource<String> hdfsLines = env.readTextFile("hdfs://nnHost:nnPort/path/to/my/textfile");

        // 从输入字符创建
        DataStreamSource<String> value = env.fromElements("Foo", "bar", "foobar", "fubar");

        // 创建一个数字序列
        DataStreamSource<Long> numbers = env.generateSequence(1, 10000000);

        env.execute("CollectionBasedDataSource");
    }

}
