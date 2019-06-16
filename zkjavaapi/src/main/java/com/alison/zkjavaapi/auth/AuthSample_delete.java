package com.alison.zkjavaapi.auth;

import com.alison.zkjavaapi.common.ZKConstant;
import org.apache.zookeeper.*;

import java.util.concurrent.CountDownLatch;

/**
 * @Author alison
 * @Date 2019/6/16  19:33
 * @Version 1.0
 */
public class AuthSample_delete {
    final static String PATH = "/zk-book-auth_test";
    final static String PATH2 = "/zk-book-auth_test/child";
    private static CountDownLatch connectedSemaphore = new CountDownLatch(1);

    public static void main(String[] args) throws Exception {
        ZooKeeper zookeeper = new ZooKeeper(ZKConstant.CONNET_STR, ZKConstant.SESSION_TIMEOUT, new MyWatcher());
        connectedSemaphore.await();// 不加同步计数器，会报错
        zookeeper.addAuthInfo("digest", "foo:true".getBytes());
        zookeeper.create(PATH, "init".getBytes(), ZooDefs.Ids.CREATOR_ALL_ACL, CreateMode.PERSISTENT);
        zookeeper.create(PATH2, "init".getBytes(), ZooDefs.Ids.CREATOR_ALL_ACL, CreateMode.EPHEMERAL);

        try {
            // 对Path加入权限之后，删除path的子路径需要加权限
            connectedSemaphore = new CountDownLatch(1);
            ZooKeeper zk1 = new ZooKeeper(ZKConstant.CONNET_STR, ZKConstant.SESSION_TIMEOUT, new MyWatcher());
            connectedSemaphore.await();// 不加同步计数器，会报错
            zk1.delete(PATH, -1);
        } catch (Exception e) {
            System.out.println("删除节点失败: " + e.getMessage());
        }
        connectedSemaphore = new CountDownLatch(1);
        ZooKeeper zk2 = new ZooKeeper(ZKConstant.CONNET_STR, ZKConstant.SESSION_TIMEOUT, new MyWatcher());
        connectedSemaphore.await();// 不加同步计数器，会报错
        zk2.addAuthInfo("digest", "foo:true".getBytes());
        zk2.delete(PATH2, -1);
        System.out.println("成功删除节点：" + PATH2);

        connectedSemaphore = new CountDownLatch(1);
        ZooKeeper zk3 = new ZooKeeper(ZKConstant.CONNET_STR, ZKConstant.SESSION_TIMEOUT, new MyWatcher());
        connectedSemaphore.await();
        zk3.delete(PATH, -1);
        System.out.println("成功删除节点：" + PATH);
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
