package com.github.labazhang.es.pool;


import org.apache.commons.pool2.impl.GenericObjectPool;
import org.apache.commons.pool2.impl.GenericObjectPoolConfig;
import org.elasticsearch.client.RestHighLevelClient;

import java.util.List;

/**
 * ES 连接池工具
 *
 * @author Laba Zhang
 */
public class EsPoolUtil {

    /**
     * 利用对象工厂类和配置类生成对象池
     */
    private static GenericObjectPool<RestHighLevelClient> clientPool = null;

    public static void init(List<EsConfig> configs) {
        // 对象池配置类，不写也可以，采用默认配置
        GenericObjectPoolConfig<RestHighLevelClient> config = new GenericObjectPoolConfig<>();
        //池中最大连接数 默认 8
        config.setMaxTotal(8);
        //最大空闲数,当超过这个数的时候关闭多余的连接 默认8
        config.setMaxIdle(8);
        //最少的空闲连接数  默认0
        config.setMinIdle(0);
        //当连接池资源耗尽时,调用者最大阻塞的时间,超时时抛出异常 单位:毫秒数   -1表示无限等待
        config.setMaxWaitMillis(10000);
        // 连接池存放池化对象方式,true放在空闲队列最前面,false放在空闲队列最后  默认为true
        config.setLifo(true);
        //连接空闲的最小时间,达到此值后空闲连接可能会被移除,默认即为30分钟
        config.setMinEvictableIdleTimeMillis(1000L * 60L * 30L);
        //连接耗尽时是否阻塞,默认为true,为false时则抛出异常
        config.setBlockWhenExhausted(true);
        //向调用者输出“链接”资源时，是否检测是有有效，如果无效则从连接池中移除，并尝试获取继续获取。默认为false。建议保持默认值.
        config.setTestOnBorrow(false);
        //把资源返回连接池时检查是否有效,默认为false
        config.setTestOnReturn(false);

        // 要池化的对象的工厂类，这个是我们要实现的类
        RestHighLevelClientFactory factory = new RestHighLevelClientFactory(configs);

        clientPool = new GenericObjectPool<>(factory, config);
    }

    /**
     * 获得对象
     *
     * @return es client
     */
    public static RestHighLevelClient getClient() throws Exception {
        if (clientPool != null) {
            return clientPool.borrowObject();
        }
        return null;
    }

    /**
     * 归还 ES 连接对象
     *
     * @param client need to returned es client
     */
    public static void returnClient(RestHighLevelClient client) {
        if (clientPool != null) {
            clientPool.returnObject(client);
        }
    }
}