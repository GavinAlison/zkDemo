package com.alison.zkservice.web;

import com.alison.zkservice.client.HelloWorldClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @Author alison
 * @Date 2019/6/23  16:04
 * @Version 1.0
 * @Description
 */
@RestController
public class GreetingController {
    @Autowired
    private HelloWorldClient helloWorldClient;

    @GetMapping("/get-greeting")
    public String greeting() {
        return helloWorldClient.HelloWorld();

    }
}

