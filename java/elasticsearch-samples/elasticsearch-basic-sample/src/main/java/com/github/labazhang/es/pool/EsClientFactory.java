package com.github.labazhang.es.pool;

import org.apache.commons.pool2.PooledObjectFactory;

import java.util.List;

/**
 * ES Client 连接池基类
 *
 * @author Laba Zhang
 */
public abstract class EsClientFactory<T> implements PooledObjectFactory<T> {

    private List<EsConfig> configs;

    protected static final String DEFAULT_SCHEMA = "http";

    public EsClientFactory(List<EsConfig> configs) {
        if (configs == null || configs.size() == 0) {
            throw new IllegalArgumentException("es connection config must not null.");
        }
        this.configs = configs;
    }

    protected EsConfig getConfig(int index) {
        return configs.get(index);
    }

    protected List<EsConfig> getConfigs() {
        return configs;
    }

    protected int getConfigSize() {
        return configs.size();
    }
}