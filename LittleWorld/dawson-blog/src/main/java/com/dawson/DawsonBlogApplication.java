package com.dawson;


import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;
import springfox.documentation.swagger2.annotations.EnableSwagger2;


@EnableSwagger2
@SpringBootApplication//启动spring必须要的注解
@MapperScan("com.dawson.mapper")//必须要扫描mapper，没搞懂目前
@EnableScheduling//开启定时任务的注解
public class DawsonBlogApplication {

    public static void main(String[] args) {
        SpringApplication.run(DawsonBlogApplication.class, args);
    }
}
