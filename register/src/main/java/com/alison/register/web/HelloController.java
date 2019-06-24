package com.alison.register.web;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @Author alison
 * @Date 2019/6/23  16:02
 * @Version 1.0
 * @Description
 */
@RestController
public class HelloController {
    @GetMapping("/helloworld")
    public String helloWorld() {
        return "Hello World!";
    }
}
