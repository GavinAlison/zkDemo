package com.alison.zkservice.client;

import org.springframework.cloud.netflix.feign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * @Author alison
 * @Date 2019/6/23  16:07
 * @Version 1.0
 * @Description
 */
@FeignClient(name = "Helloword")
public interface HelloWorldClient {
    @GetMapping("/helloworld")
    @ResponseBody
    String HelloWorld();
}
