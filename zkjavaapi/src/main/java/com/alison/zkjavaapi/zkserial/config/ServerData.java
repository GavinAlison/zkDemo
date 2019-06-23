package com.alison.zkjavaapi.zkserial.config;

import lombok.Data;

/**
 * @Author alison
 * @Date 2019/6/22  11:16
 * @Version 1.0
 * @Description 服务器基本信息
 */
@Data
public class ServerData {
    private String address;
    private Integer id;
    private String name;
}
