package com.gqzdev.aop.proxy.cglib;


import org.springframework.cglib.proxy.Callback;
import org.springframework.cglib.proxy.Enhancer;
import org.springframework.cglib.proxy.MethodInterceptor;
import org.springframework.cglib.proxy.MethodProxy;

import java.lang.reflect.Method;

/**
 * 代理模式的解释：为其他对象提供一种代理以控制对这个对象的访问，增强一个类中的某个方法，对程序进行扩展。
 */
public class UserService {

	public static void main(String[] args) {

		//目标对象
		UserService target = new UserService();

		// 通过cglib技术
		Enhancer enhancer = new Enhancer();

		//被代理类（UserService）是父类，代理类是子类，代理对象就是代理类的实例对象，代理类是由cglib创建的，
		enhancer.setSuperclass(UserService.class);


		// 定义额外逻辑，也就是代理逻辑
		enhancer.setCallbacks(new Callback[]{new MethodInterceptor() {
			@Override
			public Object intercept(Object o, Method method, Object[] objects, MethodProxy methodProxy) throws Throwable {

				System.out.println("before...");
				Object result = methodProxy.invoke(target, objects);
				System.out.println("after...");
				return result;
			}
		}});

		// 动态代理所创建出来的UserService对象
		UserService userService = (UserService) enhancer.create();

		// 执行这个userService的test方法时，就会额外会执行一些其他逻辑
		userService.test();
	}


	public void test() {
		System.out.println("test...");
	}
}