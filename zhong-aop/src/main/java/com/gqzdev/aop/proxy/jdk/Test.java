package com.gqzdev.aop.proxy.jdk;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;

/**
 * @author gqzdev
 * @date 2021/10/09 15:00
 **/
public class Test {


	public static void main(String[] args) {

		//目标对象
		UserService target = new UserService();
//		UserInterface target = new UserInterface() {
//			@Override
//			public void test() {
//				System.out.println("test");
//			}
//		};

		// UserInterface接口的代理对象
		/**
		 * 需要基于接口创建代理对象
		 * newProxyInstance(ClassLoader loader, Class<?>[] interfaces, InvocationHandler h)
		 */
		Object proxy = Proxy.newProxyInstance(UserService.class.getClassLoader(), new Class[]{UserInterface.class}, new InvocationHandler() {

			@Override
			public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
				System.out.println("before...");
				Object result = method.invoke(target, args);
				System.out.println("after...");
				return result;
			}
		});

		/**
		 * 产生的代理对象的类型是UserInterface，而不是UserService
		 */
		UserInterface userService = (UserInterface) proxy;
		userService.test();
	}
}
