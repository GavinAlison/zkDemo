package com.alison.zkjavaapi.zkserial.loadBalance;

/**
 * @Author alison
 * @Date 2019/6/22  14:33
 * @Version 1.0
 * @Description
 */
public interface RegistProvider {
    void regist(Object context) throws Exception;
    void unRegist(Object context) throws Exception;
}
