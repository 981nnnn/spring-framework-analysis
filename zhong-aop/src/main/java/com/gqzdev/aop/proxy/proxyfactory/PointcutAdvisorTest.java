package com.gqzdev.aop.proxy.proxyfactory;

import com.gqzdev.aop.proxy.jdk.UserInterface;
import com.gqzdev.aop.proxy.jdk.UserService;
import org.aopalliance.aop.Advice;
import org.aopalliance.intercept.MethodInterceptor;
import org.aopalliance.intercept.MethodInvocation;
import org.springframework.aop.Pointcut;
import org.springframework.aop.PointcutAdvisor;
import org.springframework.aop.framework.ProxyFactory;
import org.springframework.aop.support.StaticMethodMatcherPointcut;

import java.lang.reflect.Method;

/**
 * @author gqzdev
 * @date 2021/10/09 15:53
 **/
public class PointcutAdvisorTest {

	public static void main(String[] args) {

		UserService target = new UserService();

		ProxyFactory proxyFactory = new ProxyFactory();
		proxyFactory.setTarget(target);

		//一个Advisor是有一个Pointcut和一个Advice组成的，通过Pointcut可以指定要需要被代理的逻辑
		proxyFactory.addAdvisor(new PointcutAdvisor() {

			@Override
			public Pointcut getPointcut() {
				return new StaticMethodMatcherPointcut() {
					@Override
					public boolean matches(Method method, Class<?> targetClass) {
						/**
						 * 只有在执行testAbc这个方法时才会被增强，会执行额外的逻辑，而在执行其他方法时是不会增强的
						 */
						return "testAbc".equals(method.getName());
					}
				};
			}

			@Override
			public Advice getAdvice() {
				return new MethodInterceptor() {

					@Override
					public Object invoke(MethodInvocation invocation) throws Throwable {
						System.out.println("before...");
						Object result = invocation.proceed();
						System.out.println("after...");
						return result;
					}
				};
			}

			@Override
			public boolean isPerInstance() {
				return false;
			}
		});

		UserInterface userService = (UserInterface) proxyFactory.getProxy();
		userService.test();
	}
}
