package com.gqzdev.beans;

import org.springframework.context.annotation.AnnotationConfigApplicationContext;

/**
 * @author gqzdev
 * @date 2021/07/10 10:18
 *
 * 	spring中竟然有12种定义bean的方法
 *
 **/
public class BeansTest {

	public static void main(String[] args) {

		AnnotationConfigApplicationContext context = new AnnotationConfigApplicationContext(JavaConfig.class);

		for (String beanDefinitionName : context.getBeanDefinitionNames()) {
			System.out.println(beanDefinitionName);
		}

		System.out.printf("%s", context.getBean("person", Person.class).toString());
	}
}
