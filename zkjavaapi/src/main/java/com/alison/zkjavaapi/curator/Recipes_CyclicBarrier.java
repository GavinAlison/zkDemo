package com.alison.zkjavaapi.curator;

import java.io.IOException;
import java.util.concurrent.CyclicBarrier;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * @Author alison
 * @Date 2019/6/20  22:54
 * @Version 1.0
 */
public class Recipes_CyclicBarrier {
    public static CyclicBarrier barrier = new CyclicBarrier(3); // 主线程等待线程都到达某个状态就可以继续执行了

    public static void main(String[] args) throws IOException, InterruptedException {
        ExecutorService executor = Executors.newFixedThreadPool(3);
        executor.submit(new Thread(new Runner("1号选手")));
        executor.submit(new Thread(new Runner("2号选手")));
        executor.submit(new Thread(new Runner("3号选手")));
        executor.shutdown();
    }
}

class Runner implements Runnable {
    private String name;

    public Runner(String name) {
        this.name = name;
    }

    public void run() {
        System.out.println(name + " 准备好了.");
        try {
            Recipes_CyclicBarrier.barrier.await();
        } catch (Exception e) {
        }
        System.out.println(name + " 起跑!");
    }
}
