package com.alison.zkjavaapi.zkserial.config;

import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * @Author alison
 * @Date 2019/6/22  11:54
 * @Version 1.0
 * @Description
 */
@Getter
@AllArgsConstructor
public enum CmdType {
    LIST("list"), CREATE("create"), MODIFY("modify");

    private String cmd;
}
