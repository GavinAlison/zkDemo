package com.alison.register;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;

/**
 * @Author alison
 * @Date 2019/6/23  12:28
 * @Version 1.0
 * @Description
 */


@EnableDiscoveryClient
@SpringBootApplication
public class ZkApplication {
    public static void main(String[] args) {
        SpringApplication.run(ZkApplication.class, args);
    }
}
