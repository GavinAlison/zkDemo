package com.alison.zkservice;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.DiscoveryClient;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.client.loadbalancer.LoadBalancerClient;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @Author alison
 * @Date 2019/6/23  14:41
 * @Version 1.0
 * @Description
 */
@RestController
@SpringBootApplication
@EnableDiscoveryClient
public class ZkServiceApplication {
    @Autowired
    private LoadBalancerClient loadBalancer;

    @Autowired
    private DiscoveryClient discovery;

    @RequestMapping("/discovery")
    public Object discovery() {
        System.out.println(loadBalancer.choose("zktest"));
        return "discovery";
    }

    @RequestMapping("/all")
    public Object all() {
        System.out.println(discovery.getServices());
        return "all";
    }

    public static void main(String[] args) {
        SpringApplication.run(ZkServiceApplication.class, args);
    }
}
