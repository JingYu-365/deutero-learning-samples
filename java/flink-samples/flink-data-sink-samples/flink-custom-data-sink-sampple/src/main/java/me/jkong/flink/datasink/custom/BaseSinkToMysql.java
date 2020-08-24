package me.jkong.flink.datasink.custom;

import org.apache.flink.configuration.Configuration;
import org.apache.flink.streaming.api.functions.sink.RichSinkFunction;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.util.Map;

/**
 * 自定义 MySQL sink
 *
 * @author JKong
 * @version v0.0.1
 * @date 2020/8/25 07:01.
 */
public abstract class BaseSinkToMysql extends RichSinkFunction<Map<Integer, String>> {

    PreparedStatement ps;
    private Connection connection;

    /**
     * open() 方法中建立连接，这样不用每次 invoke 的时候都要建立连接和释放连接
     * <p>
     * todo: 也可以使用连接池的方式
     *
     * @param parameters 参数
     * @throws Exception e
     */
    @Override
    public void open(Configuration parameters) throws Exception {
        super.open(parameters);
        connection = getConnection();
        ps = this.connection.prepareStatement(initInsertSql());
    }

    /**
     * 传入需要执行的InsertSQL
     *
     * @return sql
     */
    public abstract String initInsertSql();

    @Override
    public void close() throws Exception {
        super.close();
        //关闭连接和释放资源
        if (connection != null) {
            connection.close();
        }
        if (ps != null) {
            ps.close();
        }
    }

    @Override
    public void invoke(Map<Integer, String> value, Context context) throws Exception {
        for (Integer index : value.keySet()) {
            ps.setObject(index, value.get(index));
        }
        ps.executeUpdate();
    }

    /**
     * 获取数据库连接
     *
     * @return connection
     */
    public abstract Connection getConnection();
}
