package com.knx.servicea;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;

@EnableDiscoveryClient
@SpringBootApplication(scanBasePackages = {"com.knx"})
public class ServiceA {

    public static void main(String[] args) {
        SpringApplication.run(ServiceA.class);
    }

}
