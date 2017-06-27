package blockingqueue.performance.storage;

import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.BlockingDeque;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingDeque;

/**
 * Created by haozhugogo on 2017/6/24. 仓库
 */
public class Storage {

    private BlockingQueue<Goods> goodsList;

    public Storage(BlockingQueue blockingQueue) {
        this.goodsList = blockingQueue;
    }

    /**
     * 生产货物
     */
    public void produce() {
        Goods goods = new Goods();
        try {

            goodsList.put(goods);

        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    /**
     * 消费货物
     */
    public void consume() {
        try {

            goodsList.take();

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
