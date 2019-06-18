package com.alison.zkjavaapi.zkClient;

import com.alison.zkjavaapi.common.ZKConstant;
import org.I0Itec.zkclient.ZkClient;
import org.I0Itec.zkclient.serialize.SerializableSerializer;

/**
 * @Author alison
 * @Date 2019/6/16  21:14
 * @Version 1.0
 */
public class Create_Node {
    public static void main(String[] args) throws Exception {
//        使用ZkClient创建节点
        ZkClient zkClient = new ZkClient(ZKConstant.CONNET_STR1, ZKConstant.SESSION_TIMEOUT, ZKConstant.CONNECT_TIMEOUT, new SerializableSerializer());
        String path = "/zk-book/c1";
        // recursion create parent
        zkClient.createPersistent(path, true);
    }
}
