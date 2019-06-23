package com.alison.zkjavaapi.zkserial.loadBalance;

import lombok.Data;

import java.io.Serializable;

/**
 * @Author alison
 * @Date 2019/6/22  14:31
 * @Version 1.0
 * @Description
 */
@Data
public class ServerData implements Serializable, Comparable<ServerData> {
    private Integer balance;
    private String host;
    private Integer port;

    @Override
    public int compareTo(ServerData o) {
        return this.getBalance().compareTo(o.getBalance());
    }
}
