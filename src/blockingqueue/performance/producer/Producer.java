package blockingqueue.performance.producer;

import blockingqueue.performance.storage.Storage;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.Callable;

/**
 * Created by haozhugogo on 2017/6/24. 生产者线程
 */
public class Producer implements Callable {

    private Storage       storage;

    private final Integer NUM = 1000;

    public Producer(Storage storage) {
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

            storage.produce();

            if (i == NUM - 1) {
                endTime = System.currentTimeMillis();
            }

        }

        Map<String, Long> statTimeAndEndTime = new HashMap<>(2);
        statTimeAndEndTime.put("startTime", startTime);
        statTimeAndEndTime.put("endTime", endTime);

        return statTimeAndEndTime;
    }
}
