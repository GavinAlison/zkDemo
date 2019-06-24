package com.alison.zkjavaapi.zkserial;

import com.alison.zkjavaapi.common.ZKConstant;
import org.I0Itec.zkclient.IZkChildListener;
import org.I0Itec.zkclient.IZkDataListener;
import org.I0Itec.zkclient.ZkClient;
import org.I0Itec.zkclient.serialize.SerializableSerializer;
import org.apache.curator.RetryPolicy;
import org.apache.curator.framework.CuratorFramework;
import org.apache.curator.framework.CuratorFrameworkFactory;
import org.apache.curator.framework.api.BackgroundCallback;
import org.apache.curator.framework.api.CuratorEvent;
import org.apache.curator.framework.recipes.cache.*;
import org.apache.curator.retry.ExponentialBackoffRetry;
import org.apache.zookeeper.CreateMode;
import org.apache.zookeeper.ZooDefs;
import org.apache.zookeeper.data.ACL;
import org.apache.zookeeper.data.Id;
import org.apache.zookeeper.data.Stat;
import org.apache.zookeeper.server.auth.DigestAuthenticationProvider;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * @Author: alison
 * @Date: 2019/6/21
 * @Version: 0.1
 **/
public class Zk_API {
    final static String createPath = "/zk_client";


    public void createSession() throws Exception {
        ZkClient zk = new ZkClient(ZKConstant.CONNET_STR1, 10000, 50000, new SerializableSerializer());
        System.out.println(zk);
        Thread.sleep(Integer.MAX_VALUE);
    }

    public void createNode() throws Exception {
        ZkClient zk = new ZkClient(ZKConstant.CONNET_STR1, 10000, 50000, new SerializableSerializer());
        User u = new User();
        u.setId(1);
        u.setName("alison");
        String path = zk.create(createPath, u, CreateMode.EPHEMERAL);
        Thread.sleep(Integer.MAX_VALUE);
    }

    public void getData() {
        ZkClient zk = new ZkClient(ZKConstant.CONNET_STR1, 10000, 50000, new SerializableSerializer());
        System.out.println("connect ok!");
        User u = new User();
        u.setId(1);
        u.setName("alison");
        String path = zk.create(createPath, u, CreateMode.EPHEMERAL);
        Stat stat = new Stat();
        u = (User) zk.readData(createPath, stat);
        System.out.println(u);
        System.out.println(stat);
        List<String> clist = zk.getChildren(createPath);
        System.out.println(clist);
        boolean b = zk.exists(createPath);
        System.out.println(b);
        boolean b1 = zk.delete(createPath);
        System.out.println(b1);
    }

    public void writeData() {
        ZkClient zk = new ZkClient(ZKConstant.CONNET_STR1, 10000, 50000, new SerializableSerializer());
        System.out.println("connect ok!");
        User u = new User();
        u.setId(1);
        u.setName("alison");
        String createPath = "/zk_client";
        String path = zk.create(createPath, u, CreateMode.EPHEMERAL);
        u.setName("alison2");
        zk.writeData(createPath, u, -1);

        User u2 = zk.readData(createPath);
        System.out.println(u2);
    }


    public void childChange() {
        ZkClient zk = new ZkClient(ZKConstant.CONNET_STR1, 10000, 50000, new SerializableSerializer());
        System.out.println("connect ok!");
        User u = new User();
        u.setId(1);
        u.setName("alison");
        String createPath = "/zk_client";
        String path = zk.create(createPath, u, CreateMode.PERSISTENT);

        zk.subscribeChildChanges(createPath, new IZkChildListener() {
            @Override
            public void handleChildChange(String parentPath, List<String> currentChilds) throws Exception {
                System.out.println(parentPath);
                System.out.println(currentChilds);
            }
        });
        zk.create(createPath + "/c1", "".getBytes(), CreateMode.EPHEMERAL);
        zk.deleteRecursive(createPath);
    }

    public void dataChange() {
        ZkClient zk = new ZkClient(ZKConstant.CONNET_STR1, 10000, 50000, new SerializableSerializer());
        System.out.println("connect ok!");
        User u = new User();
        u.setId(1);
        u.setName("alison");
        String createPath = "/zk_client";
        String path = zk.create(createPath, u, CreateMode.PERSISTENT);
        zk.subscribeDataChanges(createPath, new IZkDataListener() {
            @Override
            public void handleDataChange(String s, Object o) throws Exception {
                System.out.println(s + "--" + o);
            }

            @Override
            public void handleDataDeleted(String s) throws Exception {
                System.out.println(s);
            }
        });
        u.setName("alison2");
        zk.writeData(createPath, u);
        zk.delete(createPath);
        try {
            Thread.sleep(Integer.MAX_VALUE);
        } catch (InterruptedException e) {
        }
    }

    public void createNode_with_curator() throws Exception {
        RetryPolicy retryPolicy = new ExponentialBackoffRetry(1000, 3);
        CuratorFramework client = CuratorFrameworkFactory.builder()
                .connectString(ZKConstant.CONNET_STR1)
                .sessionTimeoutMs(50000)
                .connectionTimeoutMs(50000)
                .retryPolicy(retryPolicy)
                .build();
        client.start();

        String path = client.create()
                .creatingParentsIfNeeded()
                .withMode(CreateMode.EPHEMERAL)
                .forPath(createPath, "123".getBytes());
        System.out.println(path);
        Thread.sleep(Integer.MAX_VALUE);

    }

    public void delNode_with_curator() throws Exception {
        RetryPolicy retryPolicy = new ExponentialBackoffRetry(1000, 3);
        CuratorFramework client = CuratorFrameworkFactory.builder()
                .connectString(ZKConstant.CONNET_STR1)
                .sessionTimeoutMs(50000)
                .connectionTimeoutMs(50000)
                .retryPolicy(retryPolicy)
                .build();
        client.start();

        String path = client.create()
                .creatingParentsIfNeeded()
                .withMode(CreateMode.EPHEMERAL)
                .forPath(createPath, "123".getBytes());
        System.out.println(path);
        client.delete().guaranteed().deletingChildrenIfNeeded().withVersion(-1).forPath(createPath);
        Thread.sleep(Integer.MAX_VALUE);
    }

    public void getChild_with_curator() throws Exception {
        RetryPolicy retryPolicy = new ExponentialBackoffRetry(1000, 3);
        CuratorFramework client = CuratorFrameworkFactory.builder()
                .connectString(ZKConstant.CONNET_STR1)
                .sessionTimeoutMs(50000)
                .connectionTimeoutMs(50000)
                .retryPolicy(retryPolicy)
                .build();
        client.start();

        String path = client.create()
                .creatingParentsIfNeeded()
                .withMode(CreateMode.EPHEMERAL)
                .forPath(createPath, "123".getBytes());
        System.out.println(path);

        List<String> clist = client.getChildren().forPath(createPath);
        System.out.println(clist);
        Thread.sleep(Integer.MAX_VALUE);
    }

    public void getData_with_curator() throws Exception {
        RetryPolicy retryPolicy = new ExponentialBackoffRetry(1000, 3);
        CuratorFramework client = CuratorFrameworkFactory.builder()
                .connectString(ZKConstant.CONNET_STR1)
                .sessionTimeoutMs(50000)
                .connectionTimeoutMs(50000)
                .retryPolicy(retryPolicy)
                .build();
        client.start();
        String path = client.create()
                .creatingParentsIfNeeded()
                .withMode(CreateMode.EPHEMERAL)
                .forPath(createPath, "123".getBytes());
        System.out.println(path);

        Stat stat = new Stat();
        String s = new String(client.getData().storingStatIn(stat).forPath(createPath));
        System.out.println(s);
        System.out.println(stat);
        // update data
        client.setData().withVersion(stat.getVersion()).forPath(createPath, "333".getBytes());
    }

    RetryPolicy retryPolicy = new ExponentialBackoffRetry(1000, 3);
    CuratorFramework client = CuratorFrameworkFactory.builder()
            .connectString(ZKConstant.CONNET_STR1)
            .sessionTimeoutMs(50000)
            .connectionTimeoutMs(50000)
            .retryPolicy(retryPolicy)
            .build();

    public void checkexists() throws Exception {
        RetryPolicy retryPolicy = new ExponentialBackoffRetry(1000, 3);
        CuratorFramework client = CuratorFrameworkFactory.builder()
                .connectString(ZKConstant.CONNET_STR1)
                .sessionTimeoutMs(50000)
                .connectionTimeoutMs(50000)
                .retryPolicy(retryPolicy)
                .build();
        client.start();
        String path = client.create()
                .creatingParentsIfNeeded()
                .withMode(CreateMode.EPHEMERAL)
                .forPath(createPath, "123".getBytes());
        System.out.println(path);

        ExecutorService es = Executors.newFixedThreadPool(2);
        client.checkExists().inBackground(new BackgroundCallback() {
            @Override
            public void processResult(CuratorFramework curatorFramework, CuratorEvent curatorEvent) throws Exception {
                Stat stat = new Stat();
                System.out.println(stat);
                System.out.println(curatorEvent.getContext());
            }
        }, "555", es).forPath(createPath);

        Thread.sleep(Integer.MAX_VALUE);
    }

    public void nodeListener() throws Exception {
        client.start();
        String path = client.create()
                .creatingParentsIfNeeded()
                .withMode(CreateMode.EPHEMERAL)
                .forPath(createPath, "123".getBytes());
        System.out.println(path);

        final NodeCache cache = new NodeCache(client, createPath);
        cache.start();
        cache.getListenable().addListener(new NodeCacheListener() {
            @Override
            public void nodeChanged() throws Exception {
                byte[] b = cache.getCurrentData().getData();
                System.out.println(new String(b));
            }
        });
        Thread.sleep(Integer.MAX_VALUE);
    }

    public void nodeChildListener() throws Exception {
        client.start();

        final PathChildrenCache cache = new PathChildrenCache(client, createPath, true);
        cache.start();
        cache.getListenable().addListener(new PathChildrenCacheListener() {
            @Override
            public void childEvent(CuratorFramework curatorFramework, PathChildrenCacheEvent pathChildrenCacheEvent) throws Exception {
                switch (pathChildrenCacheEvent.getType()) {
                    case CHILD_ADDED:
                        System.out.println("child node added =" + pathChildrenCacheEvent.getData());
                    case CHILD_UPDATED:
                        System.out.println("child node update =" + pathChildrenCacheEvent.getData());
                    case CHILD_REMOVED:
                        System.out.println("child node removed =" + pathChildrenCacheEvent.getData());
                    default:
                        break;
                }
            }
        });
        Thread.sleep(Integer.MAX_VALUE);
    }

    public void createNodeAuth() throws Exception {
        client.start();
        ACL aclip = new ACL(ZooDefs.Perms.READ, new Id(ZKConstant.IP, ZKConstant.PORT));
        ACL aclDigest = new ACL(ZooDefs.Perms.READ | ZooDefs.Perms.WRITE, new Id("digest", DigestAuthenticationProvider.generateDigest("mask:true")));
        ArrayList<ACL> acls = new ArrayList<ACL>();
        acls.add(aclDigest);
        acls.add(aclip);

        String path = client.create()
                .creatingParentsIfNeeded()
                .withMode(CreateMode.EPHEMERAL)
                .forPath(createPath, "123".getBytes());
        System.out.println(path);

        Thread.sleep(Integer.MAX_VALUE);
    }

    public static void main(String[] args) throws Exception {
        Zk_API a = new Zk_API();
        a.getChild_with_curator();
    }
}
