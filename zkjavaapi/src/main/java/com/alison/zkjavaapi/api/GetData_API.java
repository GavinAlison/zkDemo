package com.alison.zkjavaapi.api;

import com.alison.zkjavaapi.common.ZKConstant;
import org.apache.zookeeper.*;
import org.apache.zookeeper.data.Stat;

import java.util.List;
import java.util.concurrent.CountDownLatch;

/**
 * @Author alison
 * @Date 2019/6/16  15:58
 * @Version 1.0
 */
public class GetData_API {
    private static ZooKeeper zk1;
    private static CountDownLatch connectedSemaphore = new CountDownLatch(1);
    private static Stat stat = new Stat();
    static String path = "/zk-book";
    public static void main(String[] args) throws Exception {
        sync_setData();
    }
    public static void sync_setData() throws Exception {
        String path = "/zk-book";
        zk1 = new ZooKeeper(ZKConstant.CONNET_STR,
                ZKConstant.SESSION_TIMEOUT, //
                new MyWatcher());
        connectedSemaphore.await();
        Stat stat = zk1.setData(path, "haha".getBytes(), -1);
        System.out.println(stat.getCzxid() + "," + stat.getMzxid() + "," +
                stat.getVersion());
        Stat stat2 = zk1.setData(path, "haha".getBytes(), -1);
        System.out.println(stat.getCzxid() + "," + stat.getMzxid() + "," +
                stat.getVersion());
        try {
            // 指定version， 需要正确的version才可以通过
            zk1.setData(path, "456".getBytes(), stat.getVersion());
        } catch (KeeperException e) {
            System.out.println("Error: " + e.code() + "," + e.getMessage());
        }
        Thread.sleep(Integer.MAX_VALUE);
    }

    public static void async_setData() throws Exception {
        String path = "/zk-book";
        zk1 = new ZooKeeper(ZKConstant.CONNET_STR,
                ZKConstant.SESSION_TIMEOUT, //
                new MyWatcher());
        connectedSemaphore.await();
        zk1.setData(path, "456".getBytes(), -1, new AsyncCallback.StatCallback() {
            public void processResult(int i, String s, Object o, Stat stat) {
                if (i == 0) {
                    System.out.println("SUCCESS");
                }
            }
        }, null);
        Thread.sleep(Integer.MAX_VALUE);
    }
    public static void sync_getChildren() throws Exception {
        String path = "/zk-book";
        zk1 = new ZooKeeper(ZKConstant.CONNET_STR,
                ZKConstant.SESSION_TIMEOUT, //
                new MyWatcher());
        connectedSemaphore.await();
//        zk1.delete(path+"/c1", 0);
        zk1.delete(path, 0);
        zk1.create(path, "".getBytes(), ZooDefs.Ids.OPEN_ACL_UNSAFE,
                CreateMode.PERSISTENT);
        zk1.create(path + "/c1", "".getBytes(), ZooDefs.Ids.OPEN_ACL_UNSAFE,
                CreateMode.PERSISTENT);
        List<String> childrenList = zk1.getChildren(path, true);
        System.out.println(childrenList);
        zk1.create(path + "/c2", "".getBytes(), ZooDefs.Ids.OPEN_ACL_UNSAFE,
                CreateMode.EPHEMERAL);
        Thread.sleep(Integer.MAX_VALUE);
    }

    public static void async_getChildren() throws Exception {
        String path = "/zk-book";
        zk1 = new ZooKeeper(ZKConstant.CONNET_STR,
                ZKConstant.SESSION_TIMEOUT, //
                new MyWatcher());
        connectedSemaphore.await();
        zk1.create(path, "".getBytes(), ZooDefs.Ids.OPEN_ACL_UNSAFE,
                CreateMode.PERSISTENT);
        zk1.create(path + "/c1", "".getBytes(), ZooDefs.Ids.OPEN_ACL_UNSAFE,
                CreateMode.PERSISTENT);
        // 只会响应一次
        zk1.getChildren(path, true, new AsyncCallback.Children2Callback() {
            public void processResult(int rc, String path, Object ctx, List<String> children, Stat stat) {
                System.out.println("Get Children znode result: [response code: " + rc + ", param path: " + path
                        + ", ctx: " + ctx + ", children list: " + children + ", stat: " + stat);
            }
        }, null);
        zk1.create(path + "/c2", "".getBytes(), ZooDefs.Ids.OPEN_ACL_UNSAFE,
                CreateMode.EPHEMERAL);
        Thread.sleep(Integer.MAX_VALUE);
    }
    public static void sync_getData() throws Exception {
        String path = "/zk-book";
        zk1 = new ZooKeeper(ZKConstant.CONNET_STR,
                ZKConstant.SESSION_TIMEOUT, //
                new MyWatcher());
        connectedSemaphore.await();
        zk1.create(path, "123".getBytes(), ZooDefs.Ids.OPEN_ACL_UNSAFE, CreateMode.EPHEMERAL);
        System.out.println(new String(zk1.getData(path, true, stat)));
        System.out.println(stat.getCzxid() + "," + stat.getMzxid() + "," + stat.getVersion());
        zk1.setData(path, "456".getBytes(), -1);
        Thread.sleep(Integer.MAX_VALUE);
    }
    public static void async_getData() throws Exception {
        zk1 = new ZooKeeper(ZKConstant.CONNET_STR,
                ZKConstant.SESSION_TIMEOUT, //
                new MyWatcher());
        connectedSemaphore.await();
        zk1.create(path, "123".getBytes(), ZooDefs.Ids.OPEN_ACL_UNSAFE, CreateMode.EPHEMERAL);
        zk1.getData(path, true, new IDataCallback(), null);
        zk1.setData(path, "456".getBytes(), -1);
        Thread.sleep(Integer.MAX_VALUE);
    }
    static class MyWatcher implements Watcher {
        public void process(WatchedEvent watchedEvent) {
            if (Event.KeeperState.SyncConnected == watchedEvent.getState()) {
                if (Event.EventType.None == watchedEvent.getType() && null == watchedEvent.getPath()) {
                    connectedSemaphore.countDown();
                } else if (watchedEvent.getType() == Event.EventType.NodeDataChanged) {
                    try {
                        zk1.getData(watchedEvent.getPath(), true, new IDataCallback(), null);
                    } catch (Exception e) {
                    }
                }
            }
        }
    }
    static class IDataCallback implements AsyncCallback.DataCallback {
        public void processResult(int rc, String path, Object ctx, byte[] data, Stat stat) {
            System.out.println(rc + ", " + path + ", " + new String(data));
            System.out.println("--" + stat.getCzxid() + "," +
                    stat.getMzxid() + "," +
                    stat.getVersion());
        }
    }
}
