package com.dawson.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import springfox.documentation.builders.ApiInfoBuilder;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.service.Contact;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;

@Configuration
public class SwaggerConfig {
    @Bean
    public Docket customDocket() {
        return new Docket(DocumentationType.SWAGGER_2)
                .apiInfo(apiInfo())
                .select()
                .apis(RequestHandlerSelectors.basePackage("com.dawson.Controller"))
                .build();
    }

    private ApiInfo apiInfo() {
        Contact contact = new Contact("hongkongdoll", "http://rjfq5llmv.hd-bkt.clouddn.com/2022/10/14/691820466e124d71921e8c9b8a4999d2.png", "hongkongdoll@gmail.com");
        return new ApiInfoBuilder()
                .title("后端接口")
                .description("一个文章分享平台")
                .contact(contact)   // 联系方式
                .version("1.1.1")  // 版本
                .build();
    }
}