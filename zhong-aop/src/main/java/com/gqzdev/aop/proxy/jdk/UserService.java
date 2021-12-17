package com.gqzdev.aop.proxy.jdk;

/**
 * 需要增强的业务类
 */
public class UserService implements UserInterface {

	@Override
	public void test() {
		System.out.println("test...");
	}

}