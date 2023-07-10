package me.chaihuo.webflux.reactor;

import org.reactivestreams.Subscriber;
import org.reactivestreams.Subscription;
import reactor.core.publisher.Flux;
import reactor.util.context.Context;

/**
 * reactor = jdk8 stream + jdk9 reactive stream
 * Mono 0-1个元素
 * Flux 0-N个元素
 *
 * @author Duanhe
 * @date 2023/07/10
 */
public class ReactorDemo {
    public static void main(String[] args) {
        String[] arr = new String[]{"1", "2", "3", "4", "5"};
        // 2. 定义订阅者
        Subscriber<Integer> subscriber = new Subscriber<>() {

            private Subscription subscription;

            @Override
            public void onSubscribe(Subscription subscription) {
                // 保存订阅关系，需要用它来给发布者响应
                this.subscription = subscription;

                // 请求一个数据
                this.subscription.request(1);
            }

            @Override
            public void onNext(Integer item) {
                // 接受到一个数据，处理
                System.out.println("接收到数据：" + item);
                // 处理完调用request再请求一个数据
                this.subscription.request(1);

                // 或者已经达到了目标，调用cancel告诉发布者不再接受数据了
                // this.subscription.cancel()
            }

            @Override
            public void onError(Throwable throwable) {
                // 出现了异常（例如处理数据的时候产生了异常）
                throwable.printStackTrace();

                // 我们可以告诉发布者，后面不接受数据了
                this.subscription.cancel();
            }

            @Override
            public void onComplete() {
                // 全部数据处理完了（发布者关闭了）
                System.out.println("处理完了！");
            }
        };

        Flux.fromArray(arr).map(Integer::parseInt).subscribe(subscriber);


        // todo 另外一种写法
        // 创建一个空的上下文对象
        Context context = Context.empty();
        // 这里就是jdk8的stream
        Flux.fromArray(arr).map(Integer::parseInt)
                // 最终操作
                // 这里就是jdk9的响应式流（reactive stream）
                .subscribe(
                        // 处理成功得到打印变量值
                        item -> System.out.println("接收到数据：" + item),
                        // 处理失败打印错误信息，相当于onError
                        System.err::println,
                        // 处理完成相当于onComplete
                        () -> System.out.println("处理完了！"),
                        // 这个参数我没弄懂是个啥,总之就是上下文
                        context
                );

    }
}
