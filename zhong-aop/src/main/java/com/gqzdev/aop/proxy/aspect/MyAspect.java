package com.gqzdev.aop.proxy.aspect;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.springframework.stereotype.Component;

@Aspect
@Component
public class MyAspect {

	/**
	 * 执行之前处理
	 * @param joinPoint
	 */
	@Before("execution(public void com.gqzdev.aop.proxy.jdk.UserService.test())")
	public void Before(JoinPoint joinPoint) {

		System.out.println("execute Before");
	}
}