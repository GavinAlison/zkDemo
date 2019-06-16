package com.alison.zkjavaapi.auth;

import com.alison.zkjavaapi.common.ZKConstant;
import org.apache.zookeeper.*;

import java.util.concurrent.CountDownLatch;

/**
 * @Author alison
 * @Date 2019/6/16  19:33
 * @Version 1.0
 */
public class AuthSample {
    final static String PATH = "/zk-book-auth_test";
    private static CountDownLatch connectedSemaphore = new CountDownLatch(1);
    //    使用含权限信息的ZooKeeper会话创建数据节点
    public static void main(String[] args) throws Exception {
        ZooKeeper zookeeper = new ZooKeeper(ZKConstant.CONNET_STR, ZKConstant.SESSION_TIMEOUT, new Watcher() {
            @Override
            public void process(WatchedEvent watchedEvent) {
                if (Event.KeeperState.SyncConnected == watchedEvent.getState()) {
                    connectedSemaphore.countDown();
                    System.out.println("connected  success");
                }
            }
        });
        connectedSemaphore.await();// 不加同步计数器，会报错
//        KeeperException$ConnectionLossException: KeeperErrorCode = ConnectionLoss for /zk-book-auth_test
//        ZooKeeper zookeeper = new ZooKeeper(ZKConstant.CONNET_STR, ZKConstant.SESSION_TIMEOUT, null);
        zookeeper.addAuthInfo("digest", "foo:true".getBytes());
        zookeeper.create(PATH, "init".getBytes(), ZooDefs.Ids.CREATOR_ALL_ACL, CreateMode.EPHEMERAL);
        Thread.sleep(Integer.MAX_VALUE);
    }
}
