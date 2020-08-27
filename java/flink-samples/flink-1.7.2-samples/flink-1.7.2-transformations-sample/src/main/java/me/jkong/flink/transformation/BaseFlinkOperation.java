package me.jkong.flink.transformation;

import me.jkong.flink.common.*;
import org.apache.flink.api.java.DataSet;
import org.apache.flink.api.java.ExecutionEnvironment;
import org.apache.flink.api.java.operators.DataSource;
import org.apache.flink.table.api.Table;
import org.apache.flink.table.api.TableEnvironment;
import org.apache.flink.table.api.java.BatchTableEnvironment;

import java.util.ArrayList;
import java.util.List;

/**
 * Filter 过滤操作
 *
 * @author JKong
 * @version v0.0.1
 * @date 2020/8/25 16:31.
 */
public abstract class BaseFlinkOperation {


    protected void executeFilterOperation() throws Exception {
        // 初始化环境
        ExecutionEnvironment env = ExecutionEnvironment.getExecutionEnvironment();
        BatchTableEnvironment tEnv = TableEnvironment.getTableEnvironment(env);

        // 添加自定义函数
        registerFunction(tEnv);

        DataSource<UserInfo> dataSource = env.fromCollection(getData());

        // 注册表 及 schema (id, name, age, gender, desc)
        tEnv.registerDataSet("userinfo", dataSource, "id, name, age, gender, birth, desc");
        Table table = tEnv.scan("userinfo");
        // 打印表的Schema
        table.printSchema();

        // 对数据进行转换
        table = initTableOperation(table);

        // 打印表的Schema
        table.printSchema();

        // 为了打印结果而进行的此步转换
        DataSet<UserInfo5> data = tEnv.toDataSet(table, UserInfo5.class);
        data.print();
    }

    /**
     * 设置table过滤条件
     *
     * @param table 表
     * @return biao
     */
    protected abstract Table initTableOperation(Table table);

    /**
     * 注册自定义函数
     *
     * @param tEnv t
     */
    protected void registerFunction(BatchTableEnvironment tEnv) {
    }

    /**
     * 获取初始化数据
     *
     * @return init data
     */
    protected static List<UserInfo> getData() {
        List<UserInfo> list = new ArrayList<UserInfo>();
        UserInfo userInfo1 = new UserInfo(11L, "xiao ming", 121, true, "1999-09-12 12:13:14", "备注1");
        UserInfo userInfo2 = new UserInfo(12L, "xiao hong", 122, false, "1999-09-13 12:13:14", "备注2");
        UserInfo userInfo3 = new UserInfo(13L, "hua hua", 123, true, "1999-09-14 12:13:14", "备注3");
        UserInfo userInfo4 = new UserInfo(14L, "xiao li zi", 124, false, "1999-09-15 12:13:14", "备注4");
        UserInfo userInfo5 = new UserInfo(15L, "da gou zi", 125, true, "1999-09-16 12:13:14", "备注5");

        list.add(userInfo1);
        list.add(userInfo2);
        list.add(userInfo3);
        list.add(userInfo4);
        list.add(userInfo5);
        return list;
    }
}