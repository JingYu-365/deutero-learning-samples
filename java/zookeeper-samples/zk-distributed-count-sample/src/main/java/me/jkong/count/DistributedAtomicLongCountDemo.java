package me.jkong.count;

import org.apache.curator.framework.CuratorFramework;
import org.apache.curator.framework.CuratorFrameworkFactory;
import org.apache.curator.framework.recipes.atomic.AtomicValue;
import org.apache.curator.framework.recipes.atomic.DistributedAtomicLong;
import org.apache.curator.retry.ExponentialBackoffRetry;
import org.apache.curator.retry.RetryNTimes;
import org.apache.curator.shaded.com.google.common.collect.Lists;
import org.apache.curator.test.TestingServer;

import java.io.IOException;
import java.util.List;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

/**
 * 一个Long类型的计数器。 除了计数的范围比SharedCount大了之外，它首先尝试使用乐观锁的方式设置计数器，
 * 如果不成功(比如期间计数器已经被其它client更新了)，它使用InterProcessMutex方式来更新计数值。
 * <p>
 * 此计数器有一系列的操作：
 * get()            获取当前值
 * increment()      加一
 * decrement()      减一
 * add()            增加特定的值
 * subtract()       减去特定的值
 * trySet()         尝试设置计数值
 * forceSet()       强制设置计数值
 *
 * @author JKong
 * @version v0.0.1
 * @date 2020/5/22 10:56.
 */
public class DistributedAtomicLongCountDemo {
    private static final int QTY = 5;
    private static final String PATH = "/examples/counter";

    public static void main(String[] args) throws IOException, Exception {
        List<DistributedAtomicLong> examples = Lists.newArrayList();
        try (TestingServer server = new TestingServer()) {
            CuratorFramework client = CuratorFrameworkFactory.newClient(server.getConnectString(), new ExponentialBackoffRetry(1000, 3));
            client.start();
            ExecutorService service = Executors.newFixedThreadPool(QTY);
            for (int i = 0; i < QTY; ++i) {
                final DistributedAtomicLong count = new DistributedAtomicLong(client, PATH, new RetryNTimes(10, 10));

                examples.add(count);
                Callable<Void> task = () -> {
                    try {
                        AtomicValue<Long> value = count.increment();
                        System.out.println("succeed: " + value.succeeded());
                        // 检查返回结果的succeeded()，它代表此操作是否成功。
                        // 如果操作成功，preValue()代表操作前的值，postValue()代表操作后的值。
                        if (value.succeeded()) {
                            System.out.println("Increment: from " + value.preValue() + " to " + value.postValue());
                        }
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                    return null;
                };
                service.submit(task);
            }

            service.shutdown();
            service.awaitTermination(10, TimeUnit.MINUTES);
            Thread.sleep(Integer.MAX_VALUE);
        }
    }
}