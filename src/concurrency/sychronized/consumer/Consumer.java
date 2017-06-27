package concurrency.sychronized.consumer;

import concurrency.sychronized.storage.Storage;

/**
 * Created by haozhugogo on 2017/6/24. 消费者线程
 */
public class Consumer implements Runnable {

    private Storage storage;

    public Consumer(Storage storage) {
        this.storage = storage;
    }

    @Override
    public void run() {

        while (true) {
            storage.consume();
            try {
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }

    }

}
