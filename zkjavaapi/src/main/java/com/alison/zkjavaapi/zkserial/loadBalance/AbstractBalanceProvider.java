package com.alison.zkjavaapi.zkserial.loadBalance;

import java.util.List;

/**
 * @Author alison
 * @Date 2019/6/22  14:39
 * @Version 1.0
 * @Description
 */
public abstract class AbstractBalanceProvider<T> implements BalanceProvider<T> {
    protected abstract T balanceAlgorithm(List<T> items);

    protected abstract List<T> getBalanceItems();

    public T getBalanceItem() {
        return balanceAlgorithm(getBalanceItems());
    }
}
