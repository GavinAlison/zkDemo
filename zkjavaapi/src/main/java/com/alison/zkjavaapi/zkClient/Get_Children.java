package com.alison.zkjavaapi.zkClient;

import com.alison.zkjavaapi.common.ZKConstant;
import org.I0Itec.zkclient.IZkChildListener;
import org.I0Itec.zkclient.ZkClient;

import java.util.List;

/**
 * @Author alison
 * @Date 2019/6/16  21:29
 * @Version 1.0
 */
public class Get_Children {

    public static void main(String[] args) throws Exception {
//        ZkClient获取子节点列表。
        String path = "/zk-book";
        ZkClient zkClient = new ZkClient(ZKConstant.CONNET_STR1, ZKConstant.CONNECT_TIMEOUT);
        zkClient.subscribeChildChanges(path, new IZkChildListener() {
            public void handleChildChange(String parentPath, List<String> currentChilds) throws Exception {
                System.out.println(parentPath + " 's child changed, currentChilds:" + currentChilds);
            }
        });

        zkClient.createPersistent(path);
        Thread.sleep(1000);
        System.out.println(zkClient.getChildren(path));
        Thread.sleep(1000);
        zkClient.createPersistent(path + "/c1");
        Thread.sleep(1000);
        zkClient.delete(path + "/c1");
        Thread.sleep(1000);
        zkClient.delete(path);
        Thread.sleep(Integer.MAX_VALUE);
    }
}
