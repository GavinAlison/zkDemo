package com.alison.zkjavaapi.zkserial.masterLeader;

import lombok.Data;

import java.io.Serializable;

/**
 * @Author alison
 * @Date 2019/6/22  7:41
 * @Version 1.0
 * @Description 工作服务器信息
 */
@Data
public class RunningData implements Serializable {
    private Long cid;
    private String name;
}
