package com.dawson.annotation;


import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * 定义的一个注释，放在方法上面，可以把sql语句请求的
 * 地址，请求的参数等打印出来。但是如果每个方法都打印
 * 详细日志实在是太繁琐了，我就没有加加个方法头上
 */


@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.METHOD)
public @interface SystemLog {
    String businessName();
}
