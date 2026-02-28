package com.gaadix.rto;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.openfeign.EnableFeignClients;

@SpringBootApplication(scanBasePackages = {"com.gaadix.rto", "com.gaadix.common"})
@EnableFeignClients
public class RTOServiceApplication {
    public static void main(String[] args) {
        SpringApplication.run(RTOServiceApplication.class, args);
    }
}
