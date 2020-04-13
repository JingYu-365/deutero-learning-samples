package me.jkong.sql.datasource;

import org.apache.spark.sql.Dataset;
import org.apache.spark.sql.Row;
import org.apache.spark.sql.SparkSession;

import java.util.Properties;

/**
 * Spark SQL还包括一个可以使用JDBC从其他数据库读取数据的数据源。
 * <p>
 * 注意：需要在Spark类路径中包含特定数据库的JDBC驱动程序。
 * <p>
 * <pre>
 * - url：要连接的JDBC URL。可以在URL中指定特定于源的连接属性。
 *          例如，jdbc:postgresql://localhost/test?user=fred&password=secret
 *
 * - dbtable：应该读取的JDBC表。请注意，FROM可以使用在SQL查询的子句中有效的任何内容。
 *          例如，您也可以在括号中使用子查询，而不是完整的表。
 *
 * - driver：用于连接到此URL的JDBC驱动程序的类名。
 *
 * - partitionColumn, lowerBound, upperBound：如果指定了任何选项，则必须全部指定这些选项。
 *          另外， numPartitions必须指定。它们描述了在从多个工作者并行读取时如何对表进行分区。 
 *          partitionColumn必须是相关表格中的数字列。
 *          请注意，lowerBound和upperBound只是用来决定分区步幅，而不是在表中过滤行。
 *          因此，表中的所有行都将被分区并返回。此选项仅适用于阅读。
 *
 * - numPartitions：表读取和写入中可用于并行的最大分区数。这还确定了最大并发JDBC连接数。
 *          如果要写入的分区数超过此限制，我们通过coalesce(numPartitions)在写入之前调用将其减少到此限制。
 *
 * - fetchsize：JDBC提取大小，用于确定每次往返要获取的行数。
 *          这可以帮助JDBC驱动程序的性能，默认为低读取大小（例如，Oracle有10行）。此选项仅适用于阅读。
 *
 * - batchsize：JDBC批处理大小，用于确定每次往返要插入的行数。
 *          这可以帮助JDBC驱动程序的性能。此选项仅适用于书写。它默认为1000。
 *
 * - isolationLevel：事务隔离级别，适用于当前连接。
 *          它可以是一个NONE，READ_COMMITTED，READ_UNCOMMITTED，REPEATABLE_READ，或SERIALIZABLE，
 *          对应于由JDBC的连接对象定义，缺省值为标准事务隔离级别READ_UNCOMMITTED。此选项仅适用于书写。
 *          请参阅文档java.sql.Connection。
 *
 * - sessionInitStatement：在向远程数据库打开每个数据库会话之后，在开始读取数据之前，
 *          此选项将执行自定义SQL语句（或PL / SQL块）。使用它来实现会话初始化代码。
 *          例：option("sessionInitStatement", """BEGIN execute immediate 'alter session set "_serial_direct_read"=true'; END;""")
 *
 * - truncate：这是JDBC编写器相关选项。当SaveMode.Overwrite启用时，
 *          此选项会导致火花截断，而不是删除和重建其现有的表。
 *          这可以更有效，并防止删除表元数据（例如，索引）。但是，在某些情况下，
 *          例如新数据具有不同的架构时，它将无法工作。它默认为false。此选项仅适用于书写。
 *
 * - createTableOptions：这是JDBC编写器相关选项。
 *          如果指定，则此选项允许在创建表时设置特定于数据库的表和分区选项
 *          （例如，CREATE TABLE t (name string) ENGINE=InnoDB.）。此选项仅适用于书写。
 *
 * - createTableColumnTypes：创建表时要使用的数据库列数据类型而不是默认值。
 *          数据类型信息应以与CREATE TABLE列语法相同的格式指定
 *          （例如："name CHAR(64), comments VARCHAR(1024)")。
 *          指定的类型应该是有效的spark sql数据类型。此选项仅适用于写入。
 *
 * - customSchema：用于从JDBC连接器读取数据的自定义架构。
 *          例如，"id DECIMAL(38, 0), name STRING"。您还可以指定部分字段，其他字段使用默认类型映射。
 *          例如，"id DECIMAL(38, 0)"。列名应与JDBC表的相应列名相同。
 *          用户可以指定Spark SQL的相应数据类型，而不是使用默认值。此选项仅适用于阅读。
 * </pre>
 *
 * @author JKong
 * @version v0.0.1
 * @date 2020/4/13 17:12.
 */
public class DataSourceByJdbc {

    public static void main(String[] args) {
        SparkSession spark = SparkSession.builder().master("local").appName("DataSourceByJdbc").getOrCreate();

        // Note: JDBC loading and saving can be achieved via either the load/save or jdbc methods
        // Loading data from a JDBC source
        Dataset<Row> jdbcDF = spark.read()
                .format("jdbc")
                .option("url", "jdbc:postgresql://10.10.32.6/test?user=fred&password=secret")
                .option("dbtable", "schema.tablename")
                .option("user", "username")
                .option("password", "password")
                .load();

        Properties connectionProperties = new Properties();
        connectionProperties.put("user", "username");
        connectionProperties.put("password", "password");
        Dataset<Row> jdbcDF2 = spark.read()
                .jdbc("jdbc:postgresql:dbserver", "schema.tablename", connectionProperties);

        // Saving data to a JDBC source
        jdbcDF.write()
                .format("jdbc")
                .option("url", "jdbc:postgresql:dbserver")
                .option("dbtable", "schema.tablename")
                .option("user", "username")
                .option("password", "password")
                .save();

        jdbcDF2.write()
                .jdbc("jdbc:postgresql:dbserver", "schema.tablename", connectionProperties);

        // Specifying create table column data types on write
        jdbcDF.write()
                .option("createTableColumnTypes", "name CHAR(64), comments VARCHAR(1024)")
                .jdbc("jdbc:postgresql:dbserver", "schema.tablename", connectionProperties);
    }


}