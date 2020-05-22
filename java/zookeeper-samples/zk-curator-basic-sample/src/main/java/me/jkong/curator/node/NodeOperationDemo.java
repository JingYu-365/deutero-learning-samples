package me.jkong.curator.node;

import org.apache.curator.RetryPolicy;
import org.apache.curator.framework.CuratorFramework;
import org.apache.curator.framework.CuratorFrameworkFactory;
import org.apache.curator.retry.ExponentialBackoffRetry;
import org.apache.zookeeper.CreateMode;
import org.apache.zookeeper.data.Stat;

/**
 * 节点操作
 *
 * @author JKong
 * @version v0.0.1
 * @date 2020/5/21 18:57.
 */
public class NodeOperationDemo {

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

        client.start();

        // 新增节点
        createNode(client);

        // 读取节点
        readNode(client);

        // 更新节点
        updateNode(client);

        // 检查节点
        checkNode(client);

        // 获取某个节点的所有子节点路径
        listNode(client);

        // 删除节点
        deleteNode(client);
    }

    /**
     * 获取某个节点的所有子节点路径
     *
     * @param client client
     */
    private static void listNode(CuratorFramework client) throws Exception {
        // 获取某个节点的所有子节点路径 (该方法的返回值为List,获得ZNode的子节点Path列表)
        client.getChildren().forPath("path");
    }

    /**
     * 检查节点是否存在
     *
     * @param client client
     */
    private static void checkNode(CuratorFramework client) throws Exception {
        // 检查节点是否存在（该方法返回一个Stat实例，用于检查ZNode是否存在的操作. ）
        client.checkExists().forPath("path");
    }

    /**
     * 更新节点
     *
     * @param client client
     * @throws Exception e
     */
    private static void updateNode(CuratorFramework client) throws Exception {
        // 更新一个节点的数据内容
        Stat stat = client.setData().forPath("path", "data".getBytes());
        System.out.println(stat.toString());
        // 更新一个节点的数据内容，强制指定版本进行更新
        client.setData().withVersion(10086).forPath("path", "data".getBytes());
    }

    /**
     * 读取节点数据
     *
     * @param client client
     * @throws Exception e
     */
    private static void readNode(CuratorFramework client) throws Exception {
        // 读取一个节点的数据内容
        client.getData().forPath("path");
        // 读取一个节点的数据内容，同时获取到该节点的stat
        Stat stat = new Stat();
        client.getData().storingStatIn(stat).forPath("path");
    }

    /**
     * 删除节点
     *
     * @param client client
     * @throws Exception e
     */
    private static void deleteNode(CuratorFramework client) throws Exception {
        // 删除一个节点
        client.delete().forPath("/path1");
        // 删除一个节点，并且递归删除其所有的子节点
        client.delete().deletingChildrenIfNeeded().forPath("/path2");
        // 删除一个节点，强制指定版本进行删除
        client.delete().withVersion(10010).forPath("/path3");
        // 删除一个节点，强制保证删除
        // guaranteed()接口是一个保障措施，只要客户端会话有效，那么Curator会在后台持续进行删除操作，直到删除节点成功。
        client.delete().guaranteed().forPath("/path4");
        // 组合式删除
        client.delete().guaranteed().deletingChildrenIfNeeded().withVersion(10010).forPath("/path5");
    }

    /**
     * 新增节点操作
     *
     * @param client client
     * @throws Exception e
     */
    private static void createNode(CuratorFramework client) throws Exception {
        // 添加节点
        client.create().forPath("/path1");
        // 创建一个节点，附带初始化内容
        client.create().forPath("/path2", "init".getBytes());
        // 创建一个节点，指定创建模式（临时节点）
        client.create().withMode(CreateMode.EPHEMERAL).forPath("/path3");
        // 创建一个节点，指定创建模式（临时节点），附带初始化内容
        client.create().withMode(CreateMode.EPHEMERAL).forPath("/path4", "init".getBytes());
        // 创建一个节点，指定创建模式（临时节点），附带初始化内容，并且自动递归创建父节点
        client.create().creatingParentContainersIfNeeded()
                .withMode(CreateMode.EPHEMERAL).forPath("/path5", "init".getBytes());
    }
}