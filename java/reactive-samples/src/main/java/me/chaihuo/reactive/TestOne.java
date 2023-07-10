package me.chaihuo.reactive;

import java.util.concurrent.Flow;
import java.util.concurrent.SubmissionPublisher;
import java.util.concurrent.TimeUnit;

/**
 * TODO
 *
 * @author Duanhe
 * @date 2023/07/09
 */
public class TestOne {
    public static void main(String[] args) throws InterruptedException {
        // 定义发布者
        SubmissionPublisher<Integer> publisher = new SubmissionPublisher<Integer>();
        // 定义订阅者
        Flow.Subscriber<Integer> subscriber = new Flow.Subscriber<Integer>() {

            private Flow.Subscription subscription;

            @Override
            public void onSubscribe(Flow.Subscription subscription) {
                System.out.println(subscription.toString());
                // 保存订阅关系
                this.subscription = subscription;
                // 告诉发布者，请求第一个数据
                this.subscription.request(1);
            }

            @Override
            public void onNext(Integer item) {
                // 接收数据打印
                System.out.println("next: " + item);

                // 取消操作
                this.subscription.cancel();
            }

            @Override
            public void onError(Throwable throwable) {
                System.out.println(throwable.toString());

                // 取消操作
                this.subscription.cancel();
            }

            @Override
            public void onComplete() {
                System.out.println("completion!");
            }
        };

        // 建立关系
        publisher.subscribe(subscriber);

        // 生产数据
        int data = 1;
        // todo 阻塞式生产数据，当sub中的缓冲队列满了就会进行阻塞，只有队列中消费了才能继续submit
        publisher.submit(data);

        // 发布者关闭
        publisher.close();

        TimeUnit.SECONDS.sleep(5);
    }
}
