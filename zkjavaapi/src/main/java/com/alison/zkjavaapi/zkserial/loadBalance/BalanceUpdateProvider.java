package com.alison.zkjavaapi.zkserial.loadBalance;

/**
 * @Author alison
 * @Date 2019/6/22  14:37
 * @Version 1.0
 * @Description
 */
public interface BalanceUpdateProvider {
    // 增加负载
    boolean addBalance(Integer step);

    // 减少负载
    boolean reduceBalance(Integer step);
}
