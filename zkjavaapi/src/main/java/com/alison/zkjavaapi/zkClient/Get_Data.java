package com.alison.zkjavaapi.zkClient;

import org.I0Itec.zkclient.IZkDataListener;
import org.I0Itec.zkclient.ZkClient;

/**
 * @Author alison
 * @Date 2019/6/16  21:30
 * @Version 1.0
 */
public class Get_Data {
    public static void main(String[] args) throws Exception {
        //ZkClient获取节点数据
        String path = "/zk-book";
        ZkClient zkClient = new ZkClient("domain1.book.zookeeper:2181", 5000);
        zkClient.createEphemeral(path, "123");

        zkClient.subscribeDataChanges(path, new IZkDataListener() {
            public void handleDataDeleted(String dataPath) throws Exception {
                System.out.println("Node " + dataPath + " deleted.");
            }

            public void handleDataChange(String dataPath, Object data) throws Exception {
                System.out.println("Node " + dataPath + " changed, new data: " + data);
            }
        });

        System.out.println(zkClient.readData(path));
        zkClient.writeData(path, "456");
        Thread.sleep(1000);
        zkClient.delete(path);
        Thread.sleep(Integer.MAX_VALUE);
    }
}