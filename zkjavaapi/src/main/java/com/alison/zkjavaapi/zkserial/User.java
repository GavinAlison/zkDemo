package com.alison.zkjavaapi.zkserial;

import lombok.Data;

import java.io.Serializable;

/**
 * @Author: alison
 * @Date: 2019/6/21
 * @Version: 0.1
 **/
@Data
public class User implements Serializable {
    private Integer id;
    private String name;
}
