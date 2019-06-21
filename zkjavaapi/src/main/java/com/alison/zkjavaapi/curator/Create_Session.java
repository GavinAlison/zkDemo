package com.alison.zkjavaapi.curator;

import com.alison.zkjavaapi.common.ZKConstant;
import org.apache.curator.RetryPolicy;
import org.apache.curator.framework.CuratorFramework;
import org.apache.curator.framework.CuratorFrameworkFactory;
import org.apache.curator.retry.ExponentialBackoffRetry;

/**
 * @Author alison
 * @Date 2019/6/20  22:27
 * @Version 1.0
 */
public class Create_Session {

    public static void main(String[] args) throws Exception {

    }

    //使用curator来创建一个ZooKeeper客户端
    public void create_session_sample() throws Exception {
        RetryPolicy retryPolicy = new ExponentialBackoffRetry(1000, 3);
        CuratorFramework client = CuratorFrameworkFactory.newClient(ZKConstant.CONNET_STR1, ZKConstant.SESSION_TIMEOUT, ZKConstant.CONNECT_TIMEOUT, retryPolicy);
        client.start();
        System.out.println(client);
        Thread.sleep(Integer.MAX_VALUE);
    }

    //使用Fluent风格的API接口来创建一个ZooKeeper客户端
    public void create_Session_Sample_fluent() throws Exception {
        RetryPolicy retryPolicy = new ExponentialBackoffRetry(1000, 3);
        CuratorFramework client =
                CuratorFrameworkFactory.builder()
                        .connectString(ZKConstant.CONNET_STR1)
                        .sessionTimeoutMs(5000)
                        .retryPolicy(retryPolicy)
                        .build();
        client.start();
        Thread.sleep(Integer.MAX_VALUE);
    }

    //使用curator来创建一个含隔离命名空间的ZooKeeper客户端
    public void create_Session_Sample_With_Namespace() throws Exception {
        RetryPolicy retryPolicy = new ExponentialBackoffRetry(1000, 3);
        CuratorFramework client =
                CuratorFrameworkFactory.builder()
                        .connectString(ZKConstant.CONNET_STR1)
                        .sessionTimeoutMs(5000)
                        .retryPolicy(retryPolicy)
                        .namespace("base")
                        .build();
        client.start();
        Thread.sleep(Integer.MAX_VALUE);
    }


}
