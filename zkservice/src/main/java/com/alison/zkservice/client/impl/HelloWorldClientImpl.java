package com.alison.zkservice.client.impl;

import com.alison.zkservice.client.HelloWorldClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * @Author alison
 * @Date 2019/6/23  16:19
 * @Version 1.0
 * @Description
 */
@Service
public class HelloWorldClientImpl {


    @Autowired
    HelloWorldClient helloWorldClient;

    public String HelloWorld() {
        return helloWorldClient.HelloWorld();
    }
}
