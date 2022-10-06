package com.dawson;


import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication//启动spring必须要的注解
@MapperScan("com.dawson.mapper")//必须要扫描mapper，没搞懂目前
public class DawsonBlogApplication {

    public static void main(String[] args) {
        SpringApplication.run(DawsonBlogApplication.class, args);
    }
}
