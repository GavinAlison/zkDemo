package com.alison.zkjavaapi.zkserial.loadBalance;

/**
 * @Author alison
 * @Date 2019/6/22  14:39
 * @Version 1.0
 * @Description
 */
public interface BalanceProvider<T> {
    T getBalanceItem();
}
