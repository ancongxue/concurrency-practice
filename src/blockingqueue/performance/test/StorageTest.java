package blockingqueue.performance.test;

import blockingqueue.performance.consumer.Consumer;
import blockingqueue.performance.producer.Producer;
import blockingqueue.performance.storage.Storage;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.*;

/**
 * Created by haozhugogo on 2017/6/24.
 */
public class StorageTest {

    // 最多线程数
    private static final Integer         THREAD_NUM     = 100;

    // 队列最大容量
    private static final Integer         MAX_SIZE       = 20;

    public static void main(String[] args) {

        try {
            Thread.sleep(2000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        LinkedBlockingDeque<Storage.Goods> goodsList5 = new LinkedBlockingDeque();
        List<Future> futureList5 = blockingQueuePerformanceTest(goodsList5);
        System.out.println("无边界的LinkedBlockingDeque压力测试结果：");
        calculateTime(futureList5);
        System.out.println("====================================");

        LinkedBlockingDeque<Storage.Goods> goodsList4 = new LinkedBlockingDeque(MAX_SIZE);
        List<Future> futureList4 = blockingQueuePerformanceTest(goodsList4);
        System.out.println("边界为" + MAX_SIZE + "的LinkedBlockingDeque压力测试结果：");
        calculateTime(futureList4);
        System.out.println("====================================");

        ArrayBlockingQueue<Storage.Goods> goodsList2 = new ArrayBlockingQueue(MAX_SIZE);
        List<Future> futureList2 = blockingQueuePerformanceTest(goodsList2);
        System.out.println("边界为" + MAX_SIZE + "的ArrayBlockingQueue压力测试结果：");
        calculateTime(futureList2);
        System.out.println("====================================");

        LinkedBlockingQueue<Storage.Goods> goodsList = new LinkedBlockingQueue();
        List<Future> futureList = blockingQueuePerformanceTest(goodsList);
        System.out.println("无边界LinkedBlockingQueue压力测试结果：");
        calculateTime(futureList);
        System.out.println("====================================");

        LinkedBlockingQueue<Storage.Goods> goodsList1 = new LinkedBlockingQueue(MAX_SIZE);
        List<Future> futureList1 = blockingQueuePerformanceTest(goodsList1);
        System.out.println("边界为" + MAX_SIZE + "的LinkedBlockingQueue压力测试结果：");
        calculateTime(futureList1);
        System.out.println("====================================");


    }

    /**
     * 执行线程
     * 
     * @param blockingQueue
     * @return
     */
    public static List<Future> blockingQueuePerformanceTest(BlockingQueue blockingQueue) {

        ExecutorService executeService = Executors.newFixedThreadPool(THREAD_NUM * 2);

        Storage storage = new Storage(blockingQueue);

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

    /**
     * 获取最大、最小、平均时间
     * 
     * @param futureList
     */
    public static void calculateTime(List<Future> futureList) {
        try {
            // 等待执行完毕
            try {
                Thread.sleep(5000);
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
}
