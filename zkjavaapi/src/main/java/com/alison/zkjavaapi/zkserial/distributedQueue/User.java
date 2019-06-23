package com.alison.zkjavaapi.zkserial.distributedQueue;

import lombok.Data;

import java.io.Serializable;

/**
 * @Author alison
 * @Date 2019/6/23  9:02
 * @Version 1.0
 * @Description
 */
@Data
public class User implements Serializable {
    String name;
    String id;
}
