package me.jkong.election;

import org.apache.curator.framework.CuratorFramework;
import org.apache.curator.framework.CuratorFrameworkFactory;
import org.apache.curator.framework.recipes.leader.LeaderLatch;
import org.apache.curator.framework.recipes.leader.LeaderLatchListener;
import org.apache.curator.retry.ExponentialBackoffRetry;
import org.apache.curator.utils.CloseableUtils;

import java.util.concurrent.CountDownLatch;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * 随机从候选者中选出一台作为 leader，直到调用 close() 释放leadship，
 * 此时再重新随机选举 leader，否则其他的候选者无法成为 leader。
 *
 * @author JKong
 */
public class LeaderLatchDemo {
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
                        // 创建客户端
                        CuratorFramework client = getClient();
                        int number = atomicInteger.getAndIncrement();
                        final LeaderLatch latch = new LeaderLatch(client, MASTER_PATH, "client#" + number);
                        System.out.println("创建客户端：" + latch.getId());
                        // 添加监听事件
                        latch.addListener(new LeaderLatchListener() {
                            @Override
                            public void isLeader() {
                                System.out.println(latch.getId() + ": Leader!");
                            }

                            @Override
                            public void notLeader() {
                                System.out.println(latch.getId() + ": Lost!");
                            }
                        });
                        try {
                            latch.start();
                            // 等待 number * 10秒后关闭
                            Thread.sleep(number * 10000);
                        } catch (Exception e) {
                            System.out.println(e.getMessage());
                        } finally {
                            System.out.println(latch.getId() + " Closed.");
                            CloseableUtils.closeQuietly(latch);
                            CloseableUtils.closeQuietly(client);
                            countDownLatch.countDown();
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
