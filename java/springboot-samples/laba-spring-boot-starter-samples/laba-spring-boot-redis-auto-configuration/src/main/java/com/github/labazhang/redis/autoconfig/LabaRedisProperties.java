package com.github.labazhang.redis.autoconfig;

import org.springframework.boot.context.properties.ConfigurationProperties;

/**
 * Redis config info
 *
 * @author laba zhang
 */
@ConfigurationProperties(prefix = "spring.laba.redis")
public class LabaRedisProperties {
    private int database = 0;
    private String host = "localhost";
    private String username;
    private String password;
    private int port = 6379;

    public int getDatabase() {
        return database;
    }

    public void setDatabase(int database) {
        this.database = database;
    }

    public String getHost() {
        return host;
    }

    public void setHost(String host) {
        this.host = host;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public int getPort() {
        return port;
    }

    public void setPort(int port) {
        this.port = port;
    }
}
