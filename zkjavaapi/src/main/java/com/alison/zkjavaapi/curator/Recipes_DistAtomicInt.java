package com.alison.zkjavaapi.curator;

import org.apache.curator.framework.CuratorFramework;
import org.apache.curator.framework.CuratorFrameworkFactory;
import org.apache.curator.framework.recipes.atomic.AtomicValue;
import org.apache.curator.framework.recipes.atomic.DistributedAtomicInteger;
import org.apache.curator.retry.ExponentialBackoffRetry;
import org.apache.curator.retry.RetryNTimes;

/**
 * @Author alison
 * @Date 2019/6/20  22:56
 * @Version 1.0
 */
public class Recipes_DistAtomicInt {
    static String distatomicint_path = "/curator_recipes_distatomicint_path";
    static CuratorFramework client = CuratorFrameworkFactory.builder()
            .connectString(ZKConstant.CONNET_STR1)
            .retryPolicy(new ExponentialBackoffRetry(1000, 3)).build();

    public static void main(String[] args) throws Exception {
        client.start();
        DistributedAtomicInteger atomicInteger =
                new DistributedAtomicInteger(client, distatomicint_path,
                        new RetryNTimes(3, 1000));
        AtomicValue<Integer> rc = atomicInteger.add(8);
        // 使用Curator实现分布式计数器
        System.out.println("Result: " + rc.succeeded());
    }
}
