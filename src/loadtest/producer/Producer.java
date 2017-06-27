package loadtest.producer;

import loadtest.storage.Storage;

import java.util.concurrent.Callable;

/**
 * Created by haozhugogo on 2017/6/24. 生产者线程
 */
public class Producer implements Callable {

    private Storage storage;

    public Producer(Storage storage) {
        this.storage = storage;
    }

    @Override
    public Long call() throws Exception {

        Long startTime = System.currentTimeMillis();

        for (int i = 0; i < 100; i++) {

            storage.produce();
        }

        Long endTime = System.currentTimeMillis();

        Long costTime = endTime - startTime;

        System.out.println(Thread.currentThread().getName() + "costTime:" + costTime);

        return costTime;
    }
}
