package concurrency.sychronized.test;

import concurrency.sychronized.consumer.Consumer;
import concurrency.sychronized.producer.Producer;
import concurrency.sychronized.storage.Storage;

/**
 * Created by haozhugogo on 2017/6/24.
 */
public class StorageTest {

    public static void main(String[] args) {

        Storage storage = new Storage();

        //生产线程
        Thread produceThread1 = new Thread(new Producer(storage));
        Thread produceThread2 = new Thread(new Producer(storage));
        Thread produceThread3 = new Thread(new Producer(storage));
        Thread produceThread4 = new Thread(new Producer(storage));
        Thread produceThread5 = new Thread(new Producer(storage));
        Thread produceThread6 = new Thread(new Producer(storage));

        //消费线程
        Thread consumer1= new Thread(new Consumer(storage));
        Thread consumer2= new Thread(new Consumer(storage));
        Thread consumer3= new Thread(new Consumer(storage));
        Thread consumer4= new Thread(new Consumer(storage));

        produceThread1.start();
        produceThread2.start();
        produceThread3.start();
        produceThread4.start();
        produceThread5.start();
        produceThread6.start();

        consumer1.start();
        consumer2.start();
        consumer3.start();
        consumer4.start();

    }
}
