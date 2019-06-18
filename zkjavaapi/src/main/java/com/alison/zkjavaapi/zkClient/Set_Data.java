package com.alison.zkjavaapi.zkClient;

import com.alison.zkjavaapi.common.ZKConstant;
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
        ZkClient zkClient = new ZkClient(ZKConstant.CONNET_STR1, ZKConstant.CONNECT_TIMEOUT);
        zkClient.createEphemeral(path, new Integer(1));
        zkClient.writeData(path, new Integer(1));
    }
}
