package com.alison.zkjavaapi.curator;

import org.apache.curator.framework.CuratorFramework;
import org.apache.curator.framework.CuratorFrameworkFactory;
import org.apache.curator.retry.ExponentialBackoffRetry;
import org.apache.curator.test.TestingServer;

import java.io.File;

/**
 * @Author alison
 * @Date 2019/6/20  23:00
 * @Version 1.0
 */
public class TestingServerDemo {
    static String path = "/zookeeper";
    public static void main(String[] args) throws Exception {
        TestingServer server = new TestingServer(2181,new File("/home/admin/zk-book-data"));

        CuratorFramework client = CuratorFrameworkFactory.builder()
                .connectString(server.getConnectString())
                .sessionTimeoutMs(5000)
                .retryPolicy(new ExponentialBackoffRetry(1000, 3))
                .build();
        client.start();
        System.out.println( client.getChildren().forPath( path ));
        server.close();
    }
}
