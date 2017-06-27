package concurrency.sychronized.storage;

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
    private final Integer MAX_SIZE  = 20;

    // 货物队列
    private List<Goods>   goodsList = new ArrayList<>();

    /**
     * 生产货物
     */
    public void produce() {

        synchronized (goodsList) {

            if (goodsList.size() < MAX_SIZE) {
                Goods goods = new Goods();
                System.out.println(Thread.currentThread().getName() + "==========仓库容量" + goodsList.size()
                                   + ",生产线程,生产货物==========");
                goodsList.add(goods);

                // 唤起等待线程
                goodsList.notifyAll();
            } else {
                try {
                    System.out.println(Thread.currentThread().getName() + "==========仓库容量" + goodsList.size()
                                       + ",生产线程,被阻塞=========");

                    // 仓库已满，当前线程等待
                    goodsList.wait();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }

        }
    }

    /**
     * 消费货物
     */
    public void consume() {

        synchronized (goodsList) {
            if (goodsList.isEmpty()) {
                try {
                    System.out.println(Thread.currentThread().getName() + "==========仓库容量" + goodsList.size()
                                       + ",消费线程,被阻塞=========");

                    // 仓库已空，当前线程等待
                    goodsList.wait();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            } else {
                System.out.println(Thread.currentThread().getName() + "=========仓库容量" + goodsList.size()
                                   + ",消费线程,消费货物==========");
                goodsList.remove(0);

                // 唤起等待线程
                goodsList.notifyAll();
            }
        }

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
