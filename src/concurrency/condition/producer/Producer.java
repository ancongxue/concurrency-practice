package concurrency.condition.producer;

import concurrency.condition.storage.Storage;

/**
 * Created by haozhugogo on 2017/6/24. 生产者线程
 */
public class Producer implements Runnable {

    private Storage storage;

    public Producer(Storage storage) {
        this.storage = storage;
    }

    @Override
    public void run() {

        while (true) {
            storage.produce();
            try {
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }
}
