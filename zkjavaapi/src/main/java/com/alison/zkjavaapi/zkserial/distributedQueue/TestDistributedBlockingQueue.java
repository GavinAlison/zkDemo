package com.alison.zkjavaapi.zkserial.distributedQueue;

import org.I0Itec.zkclient.ZkClient;
import org.I0Itec.zkclient.serialize.SerializableSerializer;

import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

/**
 * @Author alison
 * @Date 2019/6/23  9:04
 * @Version 1.0
 * @Description
 */
public class TestDistributedBlockingQueue {
    public static void main(String[] args) {
        ScheduledExecutorService delayExector = Executors.newScheduledThreadPool(1);
        int delayTime = 5;
        ZkClient zkClient = new ZkClient("192.168.56.103:2181", 5000, 50000, new SerializableSerializer());
        final DistributedBlockingQueue<User> queue = new DistributedBlockingQueue<User>(zkClient, "/Queue");

        final User user1 = new User();
        user1.setId("1");
        user1.setName("xiao wang");

        final User user2 = new User();
        user2.setId("2");
        user2.setName("xiao wang");

        try {
            delayExector.schedule(new Runnable() {
                public void run() {
                    try {
                        queue.offer(user1);
                        queue.offer(user2);
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
            }, delayTime, TimeUnit.SECONDS);

            System.out.println("ready poll!");
            User u1 = (User) queue.poll();
            User u2 = (User) queue.poll();

            if (user1.getId().equals(u1.getId()) && user2.getId().equals(u2.getId())) {
                System.out.println("Success!");
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            delayExector.shutdown();
            try {
                delayExector.awaitTermination(2, TimeUnit.SECONDS);
            } catch (InterruptedException e) {
            }
        }

    }
}
