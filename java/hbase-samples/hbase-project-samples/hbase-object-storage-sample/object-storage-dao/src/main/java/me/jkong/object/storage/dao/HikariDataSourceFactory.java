package me.jkong.object.storage.dao;

import com.zaxxer.hikari.HikariDataSource;
import org.apache.ibatis.datasource.unpooled.UnpooledDataSourceFactory;

/**
 * 配置数据源连接池
 *
 * @author JKong
 * @version v0.0.1
 * @date 2020/8/21 07:53.
 */
public class HikariDataSourceFactory extends UnpooledDataSourceFactory {

    public HikariDataSourceFactory() {
        this.dataSource = new HikariDataSource();
    }

}