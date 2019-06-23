package com.alison.zkjavaapi.zkserial.DistributedLock;

import java.util.concurrent.TimeUnit;

/**
 * @Author alison
 * @Date 2019/6/22  23:13
 * @Version 1.0
 * @Description
 */
public interface DistributedLock {
    /*
     * 获取锁，如果没有得到就等待
     */
    void acquire() throws Exception;

    /*
     * 获取锁，直到超时
     */
    boolean acquire(long time, TimeUnit unit) throws Exception;

    /*
     * 释放锁
     */
    void release() throws Exception;
}
