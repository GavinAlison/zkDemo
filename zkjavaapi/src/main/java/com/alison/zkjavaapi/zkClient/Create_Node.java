package com.alison.zkjavaapi.zkClient;

import com.alison.zkjavaapi.common.ZKConstant;
import org.I0Itec.zkclient.ZkClient;

/**
 * @Author alison
 * @Date 2019/6/16  21:14
 * @Version 1.0
 */
public class Create_Node {
    public static void main(String[] args) throws Exception {
//        使用ZkClient创建节点
        ZkClient zkClient = new ZkClient(ZKConstant.CONNET_STR, ZKConstant.SESSION_TIMEOUT);
        String path = "/zk-book/c1";
        // recursion create parent
        zkClient.createPersistent(path, true);
    }
}
