package com.gqzdev.testautowired;

import org.springframework.context.annotation.AnnotationConfigApplicationContext;

/**
 * @author ganquanzhong
 * @date 2021/11/08 15:22
 **/
public class Test {

	public static void main(String[] args) {

		AnnotationConfigApplicationContext context = new AnnotationConfigApplicationContext(JavaAutoConfig.class);

		User user = context.getBean("user", User.class);

		System.out.printf(user.toString());
	}
}
