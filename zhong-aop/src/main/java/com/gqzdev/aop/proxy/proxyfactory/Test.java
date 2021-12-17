package com.gqzdev.aop.proxy.proxyfactory;

import com.gqzdev.aop.proxy.jdk.UserInterface;
import com.gqzdev.aop.proxy.jdk.UserService;
import org.aopalliance.intercept.MethodInterceptor;
import org.aopalliance.intercept.MethodInvocation;
import org.springframework.aop.framework.ProxyFactory;


/**
 * Spring框架封装的ProxyFactory
 *
 * @author gqzdev
 * @date 2021/10/09 15:11
 **/
public class Test {

	public static void main(String[] args) {

		UserService target = new UserService();

		/**
		 * 通过ProxyFactory，我们可以不再关系到底是用cglib还是jdk动态代理了
		 *
		 * ProxyFactory会帮我们去判断，如果UserService实现了接口，那么ProxyFactory底层就会用jdk动态代理，
		 * 如果没有实现接口，就会用cglib技术，上面的代码，就是由于UserService实现了UserInterface接口，
		 * 所以最后产生的代理对象是UserInterface类型。
		 *
		 */
		ProxyFactory proxyFactory = new ProxyFactory();
		//设置代理目标对象
		proxyFactory.setTarget(target);

		proxyFactory.addAdvice(new MethodInterceptor() {
			@Override
			public Object invoke(MethodInvocation invocation) throws Throwable {
				System.out.println("before...");
				Object result = invocation.proceed();
				System.out.println("after...");
				return result;
			}
		});

		//由于目标对象是 实现了UserInterface接口。故产生代理对象也是Interface类型
		UserInterface userService = (UserInterface) proxyFactory.getProxy();
		userService.test();
	}
}
