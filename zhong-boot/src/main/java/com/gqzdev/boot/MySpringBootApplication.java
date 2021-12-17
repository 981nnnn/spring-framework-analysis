package com.gqzdev.boot;

import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;

import java.lang.annotation.*;

/**
 * 模拟SpringBootApplication注解
 *
 * 核心就是几个复合注解组成的， @Configuration、@ComponentScan
 */
@Target(ElementType.TYPE)
@Retention(RetentionPolicy.RUNTIME)
@Documented
@Inherited
@Configuration
@ComponentScan
public @interface MySpringBootApplication {
}
