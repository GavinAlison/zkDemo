package com.alison.zkjavaapi.auth;

import com.alison.zkjavaapi.common.ZKConstant;
import org.apache.zookeeper.*;

import java.util.concurrent.CountDownLatch;

/**
 * @Author alison
 * @Date 2019/6/16  19:57
 * @Version 1.0
 */
public class AuthSample_get {

    final static String PATH = "/zk-book-auth_test";
    private static CountDownLatch connectedSemaphore = new CountDownLatch(1);

    public static void main(String[] args) throws Exception {
        ZooKeeper zookeeper = new ZooKeeper(ZKConstant.CONNET_STR, ZKConstant.SESSION_TIMEOUT, new MyWatcher());
        connectedSemaphore.await();
        zookeeper.addAuthInfo("digest", "foo:true".getBytes());
        zookeeper.create(PATH, "init".getBytes(), ZooDefs.Ids.CREATOR_ALL_ACL, CreateMode.EPHEMERAL);

        try {
            connectedSemaphore = new CountDownLatch(1);
            ZooKeeper zookeeper1 = new ZooKeeper(ZKConstant.CONNET_STR, ZKConstant.SESSION_TIMEOUT, new MyWatcher());
            connectedSemaphore.await();
            // 无权限，无法获取信息
            System.out.println(new String(zookeeper1.getData(PATH, false, null)));

            connectedSemaphore = new CountDownLatch(1);
            ZooKeeper zookeeper2 = new ZooKeeper(ZKConstant.CONNET_STR, ZKConstant.SESSION_TIMEOUT, new MyWatcher());
            connectedSemaphore.await();
            zookeeper2.addAuthInfo("digest", "foo:false".getBytes());
            System.out.println(new String(zookeeper2.getData(PATH, false, null)));
        } catch (Exception e) {
            e.printStackTrace();
//          KeeperErrorCode = NoAuth for /zk-book-auth_test
        }

        connectedSemaphore = new CountDownLatch(1);
        ZooKeeper zk3 = new ZooKeeper(ZKConstant.CONNET_STR, ZKConstant.SESSION_TIMEOUT, new MyWatcher());
        connectedSemaphore.await();
        zk3.addAuthInfo("digest", "foo:true".getBytes());
        System.out.println(new String(zk3.getData(PATH, false, null)));
    }

    static class MyWatcher implements Watcher {
        @Override
        public void process(WatchedEvent watchedEvent) {
            if (watchedEvent.getState() == Event.KeeperState.SyncConnected) {
                connectedSemaphore.countDown();
                System.out.println("connected  success");
            }
        }
    }
}


