package com.alison.zkjavaapi.api;

import com.alison.zkjavaapi.common.ZKConstant;
import org.apache.zookeeper.*;

import java.util.concurrent.CountDownLatch;

/**
 * @Author alison
 * @Date 2019/6/16  19:12
 * @Version 1.0
 */
public class ExistsNode_API {
    private static CountDownLatch connectedSemaphore = new CountDownLatch(1);
    private static ZooKeeper zk;
    public static void main(String[] args) throws Exception {
        String path = "/zk-book";
        zk = new ZooKeeper(ZKConstant.CONNET_STR,
                ZKConstant.SESSION_TIMEOUT, //
                new MyWatcher());
        connectedSemaphore.await();
//         对path 路劲进行监听
        zk.exists(path, true);
        zk.create(path, "".getBytes(), ZooDefs.Ids.OPEN_ACL_UNSAFE, CreateMode.PERSISTENT);
        zk.setData(path, "123".getBytes(), -1);
        zk.create(path+"/c1", "".getBytes(), ZooDefs.Ids.OPEN_ACL_UNSAFE, CreateMode.PERSISTENT);
        zk.setData(path + "/c1", "000".getBytes(), -1);
        zk.delete(path + "/c1", -1);
        zk.delete(path, -1);
        Thread.sleep(Integer.MAX_VALUE);
    }
    static class MyWatcher implements Watcher {
        public void process(WatchedEvent watchedEvent) {
            try {
                if (Event.KeeperState.SyncConnected == watchedEvent.getState()) {
                    if (Event.EventType.None == watchedEvent.getType() && null == watchedEvent.getPath()) {
                        connectedSemaphore.countDown();
                    } else if (Event.EventType.NodeCreated == watchedEvent.getType()) {
                        System.out.println("node (" + watchedEvent.getPath() + ") created ");
                        zk.exists(watchedEvent.getPath(), true);
                    } else if (Event.EventType.NodeDeleted == watchedEvent.getType()) {
                        System.out.println("node (" + watchedEvent.getPath() + ") deleted ");
                        zk.exists(watchedEvent.getPath(), true);
                    } else if (Event.EventType.NodeDataChanged == watchedEvent.getType()) {
                        System.out.println("node (" + watchedEvent.getPath() + ") dataChanged");
                        zk.exists(watchedEvent.getPath(), true);
                    }
                }
            } catch (Exception e) {
            }
        }
    }
}
