package com.knx.serviceb;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.openfeign.EnableFeignClients;

@EnableFeignClients(basePackages = {"com.knx.api"})
@EnableDiscoveryClient
@SpringBootApplication(scanBasePackages = {"com.knx"})
public class ServiceB {

    public static void main(String[] args) {
        SpringApplication.run(ServiceB.class);
    }

}
