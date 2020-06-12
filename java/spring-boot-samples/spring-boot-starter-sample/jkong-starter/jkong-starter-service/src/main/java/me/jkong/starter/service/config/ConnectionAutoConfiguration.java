package me.jkong.starter.service.config;

import me.jkong.starter.api.IConnectionService;
import me.jkong.starter.service.H2ConnectionServiceImpl;
import me.jkong.starter.service.MysqlConnectionServiceImpl;
import me.jkong.starter.service.PostGreSqlConfig;
import me.jkong.starter.service.PostGreSqlConnectionServiceImpl;
import org.springframework.boot.autoconfigure.condition.ConditionalOnBean;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * 创建配置类，自动配置数据库连接
 *
 * @author JKong
 * @version v0.0.1
 * @date 2020/6/12 16:25.
 */
@Configuration
public class ConnectionAutoConfiguration {

    /**
     * 获取MySQL数据库连接器
     *
     * @return conn
     */
    @Bean
    @ConditionalOnProperty(prefix = "me.jkong.mysql", name = {"url", "user", "password"})
    public IConnectionService getMysqlConnectionService() {
        System.out.println("Creating MysqlConnectionServiceImpl.");
        return new MysqlConnectionServiceImpl();
    }

    /**
     * 获取postgresql数据库连接
     *
     * @return conn
     */
    @Bean
    @ConditionalOnBean(PostGreSqlConfig.class)
    public IConnectionService getPostGreSqlConnectionService() {
        System.out.println("Creating PostGreSqlConnectionServiceImpl.");
        return new PostGreSqlConnectionServiceImpl();
    }

    /**
     * 如果MySQL和postgresql都没用注入Bean，那么采用默认H2数据库连接
     *
     * @return conn
     */
    @Bean
    @ConditionalOnMissingBean(IConnectionService.class)
    public IConnectionService getH2ConnectionService() {
        System.out.println("Creating H2ConnectionServiceImpl.");
        return new H2ConnectionServiceImpl();
    }
}