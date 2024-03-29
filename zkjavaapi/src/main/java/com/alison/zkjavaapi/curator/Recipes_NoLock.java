package com.alison.zkjavaapi.curator;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.concurrent.CountDownLatch;

/**
 * @Author alison
 * @Date 2019/6/20  22:58
 * @Version 1.0
 */
public class Recipes_NoLock {
    public static void main(String[] args) throws Exception {
        final CountDownLatch down = new CountDownLatch(1);
        for(int i = 0; i < 10; i++){
            new Thread(new Runnable() {
                public void run() {
                    try {
                        down.await();
                    } catch ( Exception e ) {
                    }
                    SimpleDateFormat sdf = new SimpleDateFormat("HH:mm:ss|SSS");
                    String orderNo = sdf.format(new Date());
                    System.err.println("生成的订单号是 : "+orderNo);
                }
            }).start();
        }
        down.countDown();
    }
}
