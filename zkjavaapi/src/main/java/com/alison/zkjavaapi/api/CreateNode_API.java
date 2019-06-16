package com.alison.zkjavaapi.api;

import com.alison.zkjavaapi.common.ZKConstant;
import org.apache.zookeeper.*;

import java.util.concurrent.CountDownLatch;

/**
 * @Author alison
 * @Date 2019/6/16  9:42
 * @Version 1.0
 */
public class CreateNode_API {
    private static ZooKeeper zk1;
    private static CountDownLatch connectSemaphore = new CountDownLatch(1); // 同步计数器
    public static void main(String[] args) throws Exception {
//        createNodeASync();
        createNodeSync();
    }
    public static void createNodeSync() throws Exception {
        ZooKeeper zookeeper = new ZooKeeper(ZKConstant.CONNET_STR,
                ZKConstant.SESSION_TIMEOUT, //
                new MyWatcher());
        connectSemaphore.await();
        // 创建临时节点
        String path1 = zookeeper.create("/zk-test-ephemeral-",
                "".getBytes(),
                ZooDefs.Ids.OPEN_ACL_UNSAFE,
                CreateMode.EPHEMERAL);
        System.out.println("Success create znode: " + path1);

        String path2 = zookeeper.create("/zk-test-ephemeral-",
                "".getBytes(),
                ZooDefs.Ids.OPEN_ACL_UNSAFE,
                CreateMode.EPHEMERAL_SEQUENTIAL);
        System.out.println("Success create znode: " + path2);
    }
    public static void createNodeASync() throws Exception {
        ZooKeeper zk1 = new ZooKeeper(ZKConstant.CONNET_STR, ZKConstant.SESSION_TIMEOUT,
                new MyWatcher());
        connectSemaphore.await();
        // 创建临时节点
        zk1.create("/zk-test-eph-", "".getBytes(),
                ZooDefs.Ids.OPEN_ACL_UNSAFE, CreateMode.EPHEMERAL,
                new IStringCallback(), "I am context1.");
        zk1.create("/zk-test-eph-", "".getBytes(),
                ZooDefs.Ids.OPEN_ACL_UNSAFE, CreateMode.EPHEMERAL,
                new IStringCallback(), "i am context2");
        //  创建临时有序节点
        zk1.create("/zk-test-eph-", "".getBytes(),
                ZooDefs.Ids.OPEN_ACL_UNSAFE, CreateMode.EPHEMERAL_SEQUENTIAL,
                new IStringCallback(), "i am context3");
        Thread.sleep(Integer.MAX_VALUE);
    }
    static class MyWatcher implements Watcher {
        @Override
        public void process(WatchedEvent watchedEvent) {
//            建立连接成功回调
            if (watchedEvent.getState() == Event.KeeperState.SyncConnected) {
                connectSemaphore.countDown();
            }
        }
    }
    // 创建节点成功回调
    static class IStringCallback implements AsyncCallback.StringCallback {
        @Override
        public void processResult(int rc, String path, Object ctx, String name) {
            System.out.println("create path result: [" + rc + "," + path + "," + ctx + ", real path name:" + name);
        }
    }
}
