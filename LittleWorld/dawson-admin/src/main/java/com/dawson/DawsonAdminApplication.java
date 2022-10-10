package com.dawson;


import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
@MapperScan("com.dawson.mapper")
public class DawsonAdminApplication {
    public static void main(String[] args) {
        SpringApplication.run(DawsonAdminApplication.class, args);
    }

}
