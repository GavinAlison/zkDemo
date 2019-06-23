package com.alison.zkjavaapi.zkserial.config;

import lombok.Data;

/**
 * @Author alison
 * @Date 2019/6/22  11:15
 * @Version 1.0
 * @Description 配置信息
 */
@Data
public class ServerConfig {
    private String dbUrl;
    private String dbPwd;
    private String dbUser;
}
