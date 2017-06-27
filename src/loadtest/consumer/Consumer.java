package loadtest.consumer;

import loadtest.storage.Storage;
import java.util.concurrent.Callable;

/**
 * Created by haozhugogo on 2017/6/24. 消费者线程
 */
public class Consumer implements Callable {

    private Storage storage;

    public Consumer(Storage storage) {
        this.storage = storage;
    }

    @Override
    public Long call() throws Exception {

        Long startTime = System.currentTimeMillis();

        for (int i = 0; i < 100; i++) {

            storage.consume();
        }

        Long endTime = System.currentTimeMillis();

        Long costTime = endTime - startTime;
        System.out.println(Thread.currentThread().getName() + "：costTime:" + costTime);

        return costTime;
    }
}
