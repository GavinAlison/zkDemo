package com.alison.zkjavaapi.zkClient;

import org.I0Itec.zkclient.ZkClient;

/**
 * @Author alison
 * @Date 2019/6/16  21:30
 * @Version 1.0
 */
public class Set_Data {
    //    ZkClient更新节点数据
    public static void main(String[] args) throws Exception {
        String path = "/zk-book";
        ZkClient zkClient = new ZkClient("domain1.book.zookeeper:2181", 2000);
        zkClient.createEphemeral(path, new Integer(1));
        zkClient.writeData(path, new Integer(1));
    }
}
