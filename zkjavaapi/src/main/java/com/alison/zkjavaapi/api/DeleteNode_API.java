package com.alison.zkjavaapi.api;

import com.alison.zkjavaapi.common.ZKConstant;
import org.apache.zookeeper.*;

import java.util.concurrent.CountDownLatch;

/**
 * @Author alison
 * @Date 2019/6/16  15:52
 * @Version 1.0
 */
public class DeleteNode_API {
    private static CountDownLatch connectedSemaphore = new CountDownLatch(1);
    private static ZooKeeper zk;
    public static void main(String[] args) throws Exception {
        deleteNodeSync();
    }
    public static void deleteNodeSync() throws Exception {
        String path = "/zk_book";
        zk = new ZooKeeper(ZKConstant.CONNET_STR, ZKConstant.SESSION_TIMEOUT,
                new DeleteWatcher());
        connectedSemaphore.await();
        zk.create(path, "".getBytes(), ZooDefs.Ids.OPEN_ACL_UNSAFE, CreateMode.EPHEMERAL);
        zk.delete(path, -1);
        Thread.sleep(Integer.MAX_VALUE);
    }
    static class DeleteWatcher implements Watcher {
        @Override
        public void process(WatchedEvent watchedEvent) {
            if (Event.KeeperState.SyncConnected == watchedEvent.getState() && watchedEvent.getPath() == null) {
                connectedSemaphore.countDown();
            }
        }
    }
}
