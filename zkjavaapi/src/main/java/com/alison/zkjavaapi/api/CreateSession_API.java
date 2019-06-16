package com.alison.zkjavaapi.api;

import com.alison.zkjavaapi.common.ZKConstant;
import org.apache.zookeeper.WatchedEvent;
import org.apache.zookeeper.Watcher;
import org.apache.zookeeper.ZooKeeper;

import java.util.concurrent.CountDownLatch;

/**
 * @Author alison
 * @Date 2019/6/16  8:45
 * @Version 1.0
 */
public class CreateSession_API {
    private static ZooKeeper zk1;
    private static CountDownLatch connectSemaphore = new CountDownLatch(1);

    public static void main(String[] args) throws Exception {
//        createSession();
        createSessionWithSID();
    }

    public static void createSession() throws Exception {
        //Zookeeper是API提供的1个类，我们连接zk集群，进行相应的znode操作，都是通过ZooKeeper的实例进行，这个实例就是zk client，和命令行客户端是同样的角色
        //Zookeeper实例的创建需要传递3个参数
        //connectString 代表要连接zk集群服务，通过逗号分隔
        // 注册watcher事件
        zk1 = new ZooKeeper(ZKConstant.CONNET_STR, ZKConstant.SESSION_TIMEOUT, new Watcher() {
            public void process(WatchedEvent watchedEvent) {
//                这个方法只会调用一次，在这个session建立完成调用
                if (watchedEvent.getState() == Event.KeeperState.SyncConnected) {
                    connectSemaphore.countDown();
                    System.out.println("event:" + watchedEvent);
                    System.out.println("receive session established.");
                }
            }
        });
        System.out.println(zk1.getState());
        connectSemaphore.await();
        System.out.println("zk session established");
    }

    // 重复使用上次session, 利用sessionId和passwd
    public static void createSessionWithSID() throws Exception {
        zk1 = new ZooKeeper(ZKConstant.CONNET_STR, ZKConstant.SESSION_TIMEOUT,
                new MyWatcher());
        connectSemaphore.await();
        long sessionId = zk1.getSessionId();
        byte[] passwd = zk1.getSessionPasswd();

        zk1 = new ZooKeeper(ZKConstant.CONNET_STR, ZKConstant.SESSION_TIMEOUT,
                new MyWatcher(),
                1l, "test".getBytes());

        zk1 = new ZooKeeper(ZKConstant.CONNET_STR, ZKConstant.SESSION_TIMEOUT,
                new MyWatcher(),
                sessionId,
                passwd);
        Thread.sleep(Integer.MAX_VALUE);
    }

    static class MyWatcher implements Watcher {
        @Override
        public void process(WatchedEvent watchedEvent) {
//            只注册一次
            System.out.println("receive watched event:" + watchedEvent);
            if (Event.KeeperState.SyncConnected == watchedEvent.getState()) {
                connectSemaphore.countDown();
            }
        }
    }
}
