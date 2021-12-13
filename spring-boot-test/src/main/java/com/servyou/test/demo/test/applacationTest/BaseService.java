package com.servyou.test.demo.test.applacationTest;


import org.springframework.stereotype.Component;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Target({ElementType.METHOD,ElementType.TYPE})//注解只能用在方法上
@Retention(RetentionPolicy.RUNTIME)//保存运行策略
@Component
public @interface BaseService {
    String mes() default "注解";
}
