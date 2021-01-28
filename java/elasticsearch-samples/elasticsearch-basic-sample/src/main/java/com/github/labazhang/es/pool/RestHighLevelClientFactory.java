package com.github.labazhang.es.pool;

import org.apache.commons.pool2.PooledObject;
import org.apache.commons.pool2.impl.DefaultPooledObject;
import org.apache.http.HttpHost;
import org.elasticsearch.client.RestClient;
import org.elasticsearch.client.RestHighLevelClient;

import java.util.List;

/**
 * RestHighLevelClient 工厂类
 *
 * @author Laba Zhang
 */
public class RestHighLevelClientFactory extends EsClientFactory<RestHighLevelClient> {

    public RestHighLevelClientFactory(List<EsConfig> configs) {
        super(configs);
    }

    @Override
    public PooledObject<RestHighLevelClient> makeObject() throws Exception {
        RestHighLevelClient client = null;
        try {
            HttpHost[] hosts = new HttpHost[getConfigSize()];
            EsConfig config;
            for (int i = 0; i < hosts.length; i++) {
                config = getConfig(i);
                hosts[i] = new HttpHost(
                        config.getIp(),
                        config.getPort(),
                        config.getSchema() == null ? DEFAULT_SCHEMA : config.getSchema());
            }
            client = new RestHighLevelClient(RestClient.builder(hosts));
        } catch (Exception e) {
            e.printStackTrace();
        }
        return new DefaultPooledObject<>(client);
    }

    @Override
    public void destroyObject(PooledObject<RestHighLevelClient> p) throws Exception {
        RestHighLevelClient highLevelClient = p.getObject();
        highLevelClient.close();
    }

    @Override
    public boolean validateObject(PooledObject<RestHighLevelClient> p) {
        return false;
    }

    @Override
    public void activateObject(PooledObject<RestHighLevelClient> p) throws Exception {

    }

    @Override
    public void passivateObject(PooledObject<RestHighLevelClient> p) throws Exception {

    }
}