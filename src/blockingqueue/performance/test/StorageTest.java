package blockingqueue.performance.test;

import blockingqueue.performance.consumer.Consumer;
import blockingqueue.performance.producer.Producer;
import blockingqueue.performance.storage.Storage;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

/**
 * Created by haozhugogo on 2017/6/24.
 */
public class StorageTest {

    private static final Integer         THREAD_NUM     = 100;

    private static final ExecutorService executeService = Executors.newFixedThreadPool(THREAD_NUM * 2);

    public static void main(String[] args) {

        try {
            List<Future> futureList = blockingQueuePerformanceTest();

            // 等待执行完毕
            try {
                Thread.sleep(10000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }

            // 获取最小开始时间
            Long minStartTime = ((Map<String, Long>) futureList.get(0).get()).get("startTime");
            for (Future future : futureList) {
                Long startTime = ((Map<String, Long>) future.get()).get("startTime");

                if (startTime < minStartTime) {
                    minStartTime = startTime;
                }
            }

            System.out.println("任务开始时间：" + minStartTime);

            // 获取最大结束时间
            Long maxEndTime = ((Map<String, Long>) futureList.get(0).get()).get("endTime");
            for (Future future : futureList) {
                Long endTime = ((Map<String, Long>) future.get()).get("endTime");

                if (endTime > maxEndTime) {
                    maxEndTime = endTime;
                }
            }

            System.out.println("任务结束时间：" + maxEndTime);

            System.out.println("任务耗时：" + (maxEndTime - minStartTime));

        } catch (Exception e) {
            // ignore
        }

    }

    public static List<Future> blockingQueuePerformanceTest() {

        Storage storage = new Storage();

        List<Future> futureList = new ArrayList<>(THREAD_NUM * 2);

        for (int i = 0; i < THREAD_NUM; i++) {
            Future future = executeService.submit(new Producer(storage));
            futureList.add(future);

        }
        for (int i = 0; i < THREAD_NUM; i++) {
            Future future = executeService.submit(new Consumer(storage));
            futureList.add(future);
        }

        executeService.shutdown();

        return futureList;
    }
}
