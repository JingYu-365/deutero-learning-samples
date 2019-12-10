package me.jkong.kingbase.pool;

import com.alibaba.druid.pool.DruidDataSource;

/**
 * @author JKong
 * @version v1.0
 * @description 数据库初始连接
 * @date 2019/12/3 11:27.
 */
public class DataBaseInitializer {

    private static final String DRIVER = "com.kingbase8.Driver";

    private static final String URL = "jdbc:kingbase8://10.10.32.14:54321/WINGTEST";

    private static final String USER_NAME = "SYSTEM";

    private static final String PASSWORD = "wingtest8";

    private static final Integer INITIAL_SIZE = 1;

    private static final Integer MAX_ACTIVE = 5;


    private static DruidDataSource dataSource = null;

    static {
        initDataBase();
    }

    private static void initDataBase() {

        DefaultDataBaseConfig dataBaseConfig = new DefaultDataBaseConfig()
                .setDriver(DRIVER).setUrl(URL).setUsername(USER_NAME)
                .setPassword(PASSWORD).setInitialSize(INITIAL_SIZE).setMaxActive(MAX_ACTIVE);

        dataSource = DefaultDatabaseConnection.getInstance(dataBaseConfig).getDataSource();
    }

    public static DruidDataSource getDataBase() {
        return dataSource;
    }

}