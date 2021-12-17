package com.gqzdev.aop;

import org.springframework.stereotype.Component;

/**
 * @ClassName EnhancedUser  被增强的类
 * @Description 此bean 可能是满足业务需要的核心逻辑，
 * 例如test 方法中可能会封装着某个核心业务，
 * 但是， 如果我们想在test 前后加入日志来跟踪调试，如果直接修改源码并不符合
 * 面向对象的设计方法，而且随意改动原有代码也会造成一定的风险，还好接下来的Spring 帮我们做到了这一点。
 * 通过使用aop增强，代理类完成一些逻辑
 * @Author ganquanzhong
 * @Date2020/8/4 23:12
 * @Version
 **/
@Component
public class EnhancedUser {

	private String testStr = "testStr";

	public String getTestStr() {
		return testStr;
	}

	public void setTestStr(String testStr) {
		this.testStr = testStr;
	}

	/**
	 * 使用aop  被增强test方法
	 */
	public void test() {
		System.out.println("test");
		//int a = 1/0;
	}

}
