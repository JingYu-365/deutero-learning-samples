package com.github.labazhang.es.pool;

/**
 * ES 客户端配置
 *
 * @author Laba Zhang
 */
public class EsConfig {
    private String schema;
    private String ip;
    private Integer port;

    public String getSchema() {
        return schema;
    }

    public void setSchema(String schema) {
        this.schema = schema;
    }

    public String getIp() {
        return ip;
    }

    public void setIp(String ip) {
        this.ip = ip;
    }

    public Integer getPort() {
        return port;
    }

    public void setPort(Integer port) {
        this.port = port;
    }
}