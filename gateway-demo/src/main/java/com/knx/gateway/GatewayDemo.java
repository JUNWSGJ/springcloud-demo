package com.knx.gateway;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.scheduling.annotation.EnableScheduling;

@EnableScheduling
@EnableDiscoveryClient
@SpringBootApplication(scanBasePackages = {"com.knx"})
public class GatewayDemo {

    public static void main(String[] args) {
        SpringApplication.run(GatewayDemo.class, args);
    }

}
