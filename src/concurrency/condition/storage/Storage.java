package concurrency.condition.storage;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

/**
 * Created by haozhugogo on 2017/6/24. 仓库
 */
public class Storage {

    // 最大容量
    private final Integer   MAX_SIZE       = 20;

    // 货物队列
    private List<Goods>     goodsList      = new ArrayList<>();

    private final Lock      lock           = new ReentrantLock();

    // 仓库空的condition,用于阻塞和唤醒消费线程
    private final Condition emptyCondition = lock.newCondition();

    // 仓库满的condition,用于阻塞和唤醒生产线程
    private final Condition fullCondition  = lock.newCondition();

    /**
     * 生产货物
     */
    public void produce() {
        lock.lock();
        if (goodsList.size() < MAX_SIZE) {

            Goods goods = new Goods();
            System.out.println(Thread.currentThread().getName() + "==========仓库容量" + goodsList.size()
                               + ",生产线程,生产货物==========");
            goodsList.add(goods);

            // 唤起消费线程
            emptyCondition.signalAll();
        } else {
            try {
                // 阻塞生产线程
                System.out.println(Thread.currentThread().getName() + "==========仓库容量" + goodsList.size()
                                   + ",生产线程,被阻塞=========");
                fullCondition.await();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
        lock.unlock();

    }

    /**
     * 消费货物
     */
    public void consume() {
        lock.lock();
        if (goodsList.isEmpty()) {
            try {
                // 阻塞消费线程
                System.out.println(Thread.currentThread().getName() + "==========仓库容量" + goodsList.size()
                                   + ",消费线程,被阻塞=========");
                emptyCondition.await();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        } else {
            System.out.println(Thread.currentThread().getName() + "=========仓库容量" + goodsList.size()
                               + ",消费线程,消费货物==========");
            goodsList.remove(0);

            // 唤起生产线程
            fullCondition.signalAll();
        }
        lock.unlock();
    }

    /**
     * 货物
     */
    public class Goods {

        private double price;
        private String name;

        public double getPrice() {
            return price;
        }

        public void setPrice(double price) {
            this.price = price;
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }
    }
}
