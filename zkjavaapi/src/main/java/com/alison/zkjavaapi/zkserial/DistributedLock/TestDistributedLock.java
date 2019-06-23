package com.alison.zkjavaapi.zkserial.DistributedLock;

import org.I0Itec.zkclient.serialize.BytesPushThroughSerializer;

/**
 * @Author alison
 * @Date 2019/6/22  23:14
 * @Version 1.0
 * @Description
 */
public class TestDistributedLock {

    public static void main(String[] args) {
        final ZkClientExt zkClientExt1 = new ZkClientExt("192.168.56.103:2181", 5000,
                50000, new BytesPushThroughSerializer());
        final SimpleDistributedLockMutex mutex1 = new SimpleDistributedLockMutex(zkClientExt1, "/Mutex");

        final ZkClientExt zkClientExt2 = new ZkClientExt("192.168.56.103:2181", 5000,
                50000, new BytesPushThroughSerializer());
        final SimpleDistributedLockMutex mutex2 = new SimpleDistributedLockMutex(zkClientExt2, "/Mutex");

        try {
            mutex1.acquire();
            System.out.println("Client1 locked");
            Thread client2Thd = new Thread(new Runnable() {
                public void run() {
                    try {
                        mutex2.acquire();
                        System.out.println("Client2 locked");
                        mutex2.release();
                        System.out.println("Client2 released lock");

                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
            });
            client2Thd.start();
            Thread.sleep(5000);
            mutex1.release();
            System.out.println("Client1 released lock");

            client2Thd.join();
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

}