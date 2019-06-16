package com.alison.zkjavaapi.zkClient;

import com.alison.zkjavaapi.common.ZKConstant;
import org.I0Itec.zkclient.ZkClient;

/**
 * @Author alison
 * @Date 2019/6/16  21:28
 * @Version 1.0
 */
public class Exist_Node {
    public static void main(String[] args) throws Exception {
//        ZkClient检测节点是否存在
        String path = "/zk-book";
        ZkClient zkClient = new ZkClient(ZKConstant.CONNET_STR, ZKConstant.SESSION_TIMEOUT);
        System.out.println("Node " + path + " exists " + zkClient.exists(path));
    }
}
