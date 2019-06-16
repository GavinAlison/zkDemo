package com.alison.zkjavaapi.zkClient;

import com.alison.zkjavaapi.common.ZKConstant;
import org.I0Itec.zkclient.ZkClient;

/**
 * @Author alison
 * @Date 2019/6/16  21:27
 * @Version 1.0
 */
public class Del_Data {
    public static void main(String[] args) throws Exception {
//        ZkClient删除节点数据
        String path = "/zk-book";
        ZkClient zkClient = new ZkClient(ZKConstant.CONNET_STR, ZKConstant.SESSION_TIMEOUT);
        zkClient.createPersistent(path, "");
        zkClient.createPersistent(path+"/c1", "");
        zkClient.deleteRecursive(path);
    }
}
