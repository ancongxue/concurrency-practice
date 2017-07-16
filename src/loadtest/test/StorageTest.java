package loadtest.test;

import loadtest.consumer.Consumer;
import loadtest.producer.Producer;
import loadtest.storage.Storage;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.*;

/**
 * Created by haozhugogo on 2017/6/24.
 */
public class StorageTest {

    private static final Integer         THREAD_NUM         = 100;

    private static final Integer         COUNT_DOWN_LATCH_NUM = THREAD_NUM * 2;

    private static final ExecutorService executeService     = Executors.newFixedThreadPool(THREAD_NUM * 2);

    private static CountDownLatch        countDownLatch;

    public static void main(String[] args) {

        try {
            countDownLatch = new CountDownLatch(COUNT_DOWN_LATCH_NUM);

            List<Future> futureList = loadTest(countDownLatch);

            // 等待线程执行完成
            countDownLatch.await();

            // 获取平均值
            Long sum = 0L;
            for (Future future : futureList) {
                sum += (Long) future.get();
            }

            Long avg = sum / futureList.size();

            System.out.println("线程响应平均时间：" + avg);

            // 获取最大值,最小值
            Long max = (Long) futureList.get(0).get();
            Long min = (Long) futureList.get(0).get();
            for (Future future : futureList) {

                if ((Long) future.get() > max) {
                    max = (Long) future.get();
                }

                if ((Long) future.get() < min) {
                    min = (Long) future.get();
                }
            }
            System.out.println("线程最大响应时间：" + max);

            System.out.println("线程最小响应时间：" + min);

        } catch (Exception e) {
            // ignore
        }

    }

    public static List<Future> loadTest(CountDownLatch countDownLatch) {

        Storage storage = new Storage();
        List<Future> futureList = new ArrayList<>(THREAD_NUM * 2);

        for (int i = 0; i < THREAD_NUM; i++) {

            Future future = executeService.submit(new Producer(storage, countDownLatch));
            futureList.add(future);

        }
        for (int i = 0; i < THREAD_NUM; i++) {

            Future future = executeService.submit(new Consumer(storage, countDownLatch));
            futureList.add(future);
        }

        return futureList;

    }
}
