package com.alison.zkjavaapi.zkClient;

import com.alison.zkjavaapi.common.ZKConstant;
import org.I0Itec.zkclient.ZkClient;
import org.I0Itec.zkclient.serialize.SerializableSerializer;


/**
 * @Author alison
 * @Date 2019/6/16  21:17
 * @Version 1.0
 */
public class Create_Session {
    //     使用ZkClient来创建一个ZooKeeper客户端, 连接本地虚拟机用时42s
    public static void main(String[] args) throws Exception {
        ZkClient zkClient = new ZkClient(ZKConstant.CONNET_STR1, ZKConstant.SESSION_TIMEOUT, ZKConstant.CONNECT_TIMEOUT, new SerializableSerializer());
        System.out.println("ZooKeeper session established.");
        System.out.println(zkClient);
    }
}
