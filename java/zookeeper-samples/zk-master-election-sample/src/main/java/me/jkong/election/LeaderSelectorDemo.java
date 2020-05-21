package me.jkong.election;

import org.apache.curator.framework.CuratorFramework;
import org.apache.curator.framework.CuratorFrameworkFactory;
import org.apache.curator.framework.imps.CuratorFrameworkState;
import org.apache.curator.framework.recipes.leader.LeaderSelector;
import org.apache.curator.framework.recipes.leader.LeaderSelectorListener;
import org.apache.curator.framework.state.ConnectionState;
import org.apache.curator.retry.ExponentialBackoffRetry;
import org.apache.curator.utils.CloseableUtils;

import java.util.concurrent.CountDownLatch;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * 当节点被选为leader之后，将调用 takeLeadership 方法进行业务逻辑处理，
 * 处理完成会立即释放 leadship，重新进行Master选举。
 *
 * @author JKong
 */
public class LeaderSelectorDemo {
    private static final String ZK_SERVER_IPS = "10.10.32.17:2181,10.10.32.17:2181,10.10.32.17:2181";
    private static final String MASTER_PATH = "/ZK/leader";

    public static void main(String[] args) {
        final int clientNums = 5;
        final CountDownLatch countDownLatch = new CountDownLatch(clientNums);
        AtomicInteger atomicInteger = new AtomicInteger(1);
        try {
            for (int i = 0; i < clientNums; i++) {
                new Thread(new Runnable() {
                    @Override
                    public void run() {
                        CuratorFramework client = getClient();
                        int number = atomicInteger.getAndIncrement();
                        final String name = "client#" + number;
                        final LeaderSelector selector = new LeaderSelector(client, MASTER_PATH, new LeaderSelectorListener() {
                            @Override
                            public void takeLeadership(CuratorFramework client) throws Exception {
                                System.out.println(name + ": Leader!");
                                Thread.sleep(3000);
                            }

                            @Override
                            public void stateChanged(CuratorFramework curatorFramework, ConnectionState connectionState) {
                            }
                        });
                        System.out.println("Client：" + name);
                        try {
                            selector.autoRequeue();
                            selector.start();
                            // 等待 number * 10秒后关闭
                            Thread.sleep(number * 10000);
                        } catch (Exception e) {
                            System.out.println(e.getMessage());
                        } finally {
                            countDownLatch.countDown();
                            System.out.println(name + " Closed.");
                            CloseableUtils.closeQuietly(selector);
                            if (!client.getState().equals(CuratorFrameworkState.STOPPED)) {
                                CloseableUtils.closeQuietly(client);
                            }
                        }
                    }
                }).start();
            }
            // 等待，只有所有线程都退出
            countDownLatch.await();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private static synchronized CuratorFramework getClient() {
        CuratorFramework client = CuratorFrameworkFactory.builder().connectString(ZK_SERVER_IPS)
                .sessionTimeoutMs(6000).connectionTimeoutMs(3000)
                .retryPolicy(new ExponentialBackoffRetry(1000, 3)).build();
        client.start();
        return client;
    }
}
