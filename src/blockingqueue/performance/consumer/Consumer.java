package blockingqueue.performance.consumer;

import blockingqueue.performance.storage.Storage;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.Callable;

/**
 * Created by haozhugogo on 2017/6/24. 消费者线程
 */
public class Consumer implements Callable {

    private Storage       storage;

    private final Integer NUM = 1000;

    public Consumer(Storage storage) {
        this.storage = storage;
    }

    @Override
    public Object call() throws Exception {

        Long startTime = null;
        Long endTime = null;

        for (int i = 0; i < NUM; i++) {
            if (i == 0) {
                startTime = System.currentTimeMillis();
            }
            storage.consume();
            if (i == NUM - 1) {
                endTime = System.currentTimeMillis();
            }
        }

//        System.out.println(Thread.currentThread().getName() + " startTime: " + startTime);
//        System.out.println(Thread.currentThread().getName() + " endTime: " + endTime);

        Map<String, Long> statTimeAndEndTime = new HashMap<>(1);
        statTimeAndEndTime.put("startTime", startTime);
        statTimeAndEndTime.put("endTime", endTime);

        return statTimeAndEndTime;
    }
}
