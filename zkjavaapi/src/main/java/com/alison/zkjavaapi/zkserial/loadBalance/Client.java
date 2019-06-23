package com.alison.zkjavaapi.zkserial.loadBalance;

/**
 * @Author alison
 * @Date 2019/6/22  14:38
 * @Version 1.0
 * @Description
 */
public interface Client {
    // 连接服务器
      void connect() throws Exception;
    // 断开服务器
      void disConnect() throws Exception;
}
