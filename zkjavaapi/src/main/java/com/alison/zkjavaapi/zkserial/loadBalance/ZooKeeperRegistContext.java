package com.alison.zkjavaapi.zkserial.loadBalance;

import lombok.AllArgsConstructor;
import lombok.Data;
import org.I0Itec.zkclient.ZkClient;

/**
 * @Author alison
 * @Date 2019/6/22  14:35
 * @Version 1.0
 * @Description
 */
@Data
@AllArgsConstructor
public class ZooKeeperRegistContext {
    private String path;
    private ZkClient zkClient;
    private Object data;
}
