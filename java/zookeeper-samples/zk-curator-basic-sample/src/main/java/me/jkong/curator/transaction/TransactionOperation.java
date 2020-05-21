package me.jkong.curator.transaction;

import org.apache.curator.RetryPolicy;
import org.apache.curator.framework.CuratorFramework;
import org.apache.curator.framework.CuratorFrameworkFactory;
import org.apache.curator.retry.ExponentialBackoffRetry;
import org.apache.zookeeper.CreateMode;

/**
 * zookeeper 事务操作
 * <p>
 * CuratorFramework的实例包含inTransaction( )接口方法，调用此方法开启一个ZooKeeper事务.
 * 可以复合create, setData, check, and/or delete 等操作然后调用commit()作为一个原子操作提交。
 *
 * @author JKong
 * @version v0.0.1
 * @date 2020/5/21 19:32.
 */
public class TransactionOperation {

    final static String ZOOKEEPER_ADDRESS = "10.10.32.17:2181,10.10.32.17:2181,10.10.32.17:2181";

    public static void main(String[] args) throws Exception {
        RetryPolicy retryPolicy = new ExponentialBackoffRetry(1000, 3);
        CuratorFramework client = CuratorFrameworkFactory.builder()
                .connectString(ZOOKEEPER_ADDRESS)
                .sessionTimeoutMs(5000)
                .connectionTimeoutMs(5000)
                .retryPolicy(retryPolicy)
                .namespace("zk")
                .build();

        client.inTransaction().check().forPath("path")
                .and()
                .create().withMode(CreateMode.EPHEMERAL).forPath("path", "data".getBytes())
                .and()
                .setData().withVersion(10086).forPath("path", "data2".getBytes())
                .and()
                .commit();
    }
}