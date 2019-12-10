package me.jkong.kingbase.pool;

import com.alibaba.druid.pool.DruidDataSource;
import lombok.Getter;
import org.apache.commons.lang3.StringUtils;

/**
 * @author JKong
 * @version v1.0
 * @description 数据库连接
 * @date 2019/11/13 15:25.
 */
@Getter
public class DefaultDatabaseConnection {

    /**
     * 配置信息
     */
    private DefaultDataBaseConfig config;

    /**
     * 数据库连接
     */
    private DruidDataSource dataSource;

    /**
     * 初始化大小
     */
    private static final int INITIAL_SIZE = 1;

    /**
     * 最小连接
     */
    private static final int MIN_IDLE = 1;

    /**
     * 最大活跃数
     */
    private static final int MAX_ACTIVE = Runtime.getRuntime().availableProcessors() * 4;

    private static final String VALIDATION_QUERY = "select 'x'";

    private DefaultDatabaseConnection(DefaultDataBaseConfig config) {
        this.config = config;
        this.initDataResource();
    }

    /**
     * 获取实例
     *
     * @param config 数据库配置信息
     * @return DefaultDatabaseConnection
     */
    public static DefaultDatabaseConnection getInstance(DefaultDataBaseConfig config) {
        return new DefaultDatabaseConnection(config);
    }

    public void initDataResource() {
        dataSource = new DruidDataSource();
        if (StringUtils.isBlank(config.getDriver())) {
            throw new NullPointerException("database config: driver");
        }
        dataSource.setDriverClassName(config.getDriver());

        if (StringUtils.isBlank(config.getUrl())) {
            throw new NullPointerException("database config: url");
        }
        dataSource.setUrl(config.getUrl());

        if (StringUtils.isBlank(config.getUsername()) || StringUtils.isBlank(config.getPassword())) {
            throw new NullPointerException("database config: username or password");
        }
        dataSource.setUsername(config.getUsername());
        dataSource.setPassword(config.getPassword());

        dataSource.setInitialSize(config.getInitialSize() == null ? INITIAL_SIZE : config.getInitialSize());
        dataSource.setMinIdle(config.getInitialSize() == null ? MIN_IDLE : config.getInitialSize());
        dataSource.setMaxActive(config.getMaxActive() == null ? MAX_ACTIVE : config.getMaxActive());

        dataSource.setValidationQuery(VALIDATION_QUERY);

        // 如果连接空闲并且超过minIdle以外的连接，如果空闲时间超过minEvictableIdleTimeMillis设置的值则直接物理关闭。
        // 2.在minIdle以内的不处理。
        dataSource.setTimeBetweenEvictionRunsMillis(600 * 1000);
        // 配置一个连接在池中最大空闲时间，单位是毫秒
        dataSource.setMinEvictableIdleTimeMillis(60 * 30 * 1000);
        // 检验连接是否有效的查询语句。如果数据库Driver支持ping()方法，则优先使用ping()方法进行检查，
        // 否则使用validationQuery查询进行检查。
        // 设置从连接池获取连接时是否检查连接有效性，true时，每次都检查;false时，不检查
        dataSource.setTestOnBorrow(false);
        // 设置往连接池归还连接时是否检查连接有效性，true时，每次都检查;false时，不检查
        dataSource.setTestOnReturn(false);
        // 设置从连接池获取连接时是否检查连接有效性，true时，如果连接空闲时间超过minEvictableIdleTimeMillis进行检查，
        // 否则不检查;false时，不检查
        dataSource.setTestWhileIdle(true);
        // 打开后，增强timeBetweenEvictionRunsMillis的周期性连接检查，minIdle内的空闲连接，每次检查强制验证连接有效性.
        dataSource.setKeepAlive(true);

        // 连接泄露检查，打开removeAbandoned功能 , 连接从连接池借出后，长时间不归还，将触发强制回连接。
        // 回收周期随timeBetweenEvictionRunsMillis进行，如果连接为从连接池借出状态，并且未执行任何sql，
        // 并且从借出时间起已超过removeAbandonedTimeout时间，则强制归还连接到连接池中。
        dataSource.setRemoveAbandoned(true);
        dataSource.setRemoveAbandonedTimeout(300 * 1000);
        // 关闭abanded连接时输出错误日志，这样出现连接泄露时可以通过错误日志定位忘记关闭连接的位置
        dataSource.setLogAbandoned(true);

        dataSource.setPoolPreparedStatements(true);
        dataSource.setMaxPoolPreparedStatementPerConnectionSize(10);
        // 配置从连接池获取连接等待超时的时间
        dataSource.setMaxWait(6000);

    }
}