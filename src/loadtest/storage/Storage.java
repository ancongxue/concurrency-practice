package loadtest.storage;


import java.util.concurrent.ArrayBlockingQueue;

/**
 * Created by haozhugogo on 2017/6/24. 仓库
 */
public class Storage {

    // 最大容量
    private static final Integer       MAX_SIZE  = 20;

    // ArrayBlockingQueue 指定容量为20
     ArrayBlockingQueue<Goods> goodsList = new ArrayBlockingQueue(MAX_SIZE);


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
