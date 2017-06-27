package concurrency.blockingqueue.storage;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.BlockingDeque;
import java.util.concurrent.LinkedBlockingDeque;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

/**
 * Created by haozhugogo on 2017/6/24. 仓库
 */
public class Storage {

    // 最大容量
    private final Integer              MAX_SIZE  = 20;

    // 货物队列,指定容量为20
    private LinkedBlockingDeque<Goods> goodsList = new LinkedBlockingDeque(MAX_SIZE);

    /**
     * 生产货物
     */
    public void produce() {
        Goods goods = new Goods();
        try {
            if (goodsList.size() < MAX_SIZE) {
                System.out.println(Thread.currentThread().getName() + "==========仓库容量：" + goodsList.size()
                                   + ",生产线程,生产货物==========");
                goodsList.put(goods);

            } else {
                System.out.println(Thread.currentThread().getName() + "==========仓库容量已满：" + goodsList.size()
                                   + ",生产线程暂不能生产");
            }

        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    /**
     * 消费货物
     */
    public void consume() {
        Goods goods = new Goods();
        try {
            if (goodsList.isEmpty()) {
                System.out.println(Thread.currentThread().getName() + "==========仓库容量已空：" + goodsList.size()
                                   + ",消费线程暂不能消费");
            } else {
                System.out.println(Thread.currentThread().getName() + "=========仓库容量：" + goodsList.size()
                                   + ",消费线程,消费货物==========");
                goodsList.take();
            }

        } catch (InterruptedException e) {
            e.printStackTrace();
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
