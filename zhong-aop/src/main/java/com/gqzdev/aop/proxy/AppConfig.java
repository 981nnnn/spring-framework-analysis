package com.gqzdev.aop.proxy;

import com.gqzdev.aop.proxy.advice.MyAfterReturningAdvise;
import com.gqzdev.aop.proxy.jdk.UserService;
import org.aopalliance.intercept.MethodInterceptor;
import org.aopalliance.intercept.MethodInvocation;
import org.springframework.aop.framework.ProxyFactoryBean;
import org.springframework.aop.framework.autoproxy.BeanNameAutoProxyCreator;
import org.springframework.aop.framework.autoproxy.DefaultAdvisorAutoProxyCreator;
import org.springframework.aop.support.DefaultPointcutAdvisor;
import org.springframework.aop.support.NameMatchMethodPointcut;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.EnableAspectJAutoProxy;

/**
 * @ClassName: JavaConfig 配置类
 * @author: gqzdev
 * @date: 2021/9/2 0:22
 */
@EnableAspectJAutoProxy
@Configuration
@ComponentScan("com.gqzdev.aop.proxy")
public class AppConfig {

	/**
	 * 通过这种方法来定义一个UserService的Bean，并且是经过了AOP的。但是这种方式只能针对某一个Bean。
	 * <p>
	 * 它是一个FactoryBean，所以利用的就是FactoryBean技术，间接的将UserService的代理对象作为了Bean。
	 *
	 * @return ProxyFactoryBean
	 */
//	@Bean
	public ProxyFactoryBean userServiceProxy() {

		UserService userService = new UserService();

		ProxyFactoryBean proxyFactoryBean = new ProxyFactoryBean();
		proxyFactoryBean.setTarget(userService);

		proxyFactoryBean.addAdvice(new MethodInterceptor() {
			@Override
			public Object invoke(MethodInvocation invocation) throws Throwable {
				System.out.println("before...");
				Object result = invocation.proceed();
				System.out.println("after...");
				return result;
			}
		});
		return proxyFactoryBean;
	}


	/**
     * ProxyFactoryBean还有额外的功能，比如可以把某个Advise或Advisor定义成为Bean，
	 *
	 * 然后在ProxyFactoryBean中进行设置
	 * @return
     */
	@Bean
	public MethodInterceptor myAroundAdvise(){

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

	@Bean
	public ProxyFactoryBean userService(){
		UserService userService = new UserService();
		ProxyFactoryBean proxyFactoryBean = new ProxyFactoryBean();
		proxyFactoryBean.setTarget(userService);
		proxyFactoryBean.setInterceptorNames("myAroundAdvise");
		return proxyFactoryBean;
	}


	/**
	 * ProxyFactoryBean得自己指定被代理的对象
	 *
	 * 可以通过BeanNameAutoProxyCreator来通过指定某个bean的名字，来对该bean进行代理
	 *
	 * 通过BeanNameAutoProxyCreator可以对批量的Bean进行AOP，并且指定了代理逻辑，指定了一个
	 * InterceptorName，也就是一个Advise，前提条件是这个Advise也得是一个Bean，这样Spring才能
	 * 找到的，但是BeanNameAutoProxyCreator的缺点很明显，它只能根据beanName来指定想要代理
	 * 的Bean。
	 *
	 * @return
     */
//	@Bean
	public BeanNameAutoProxyCreator beanNameAutoProxyCreator() {
		BeanNameAutoProxyCreator beanNameAutoProxyCreator = new BeanNameAutoProxyCreator();
		beanNameAutoProxyCreator.setBeanNames("userSe*");

		//前提条件是这个Advise也得是一个Bean
		beanNameAutoProxyCreator.setInterceptorNames("myAroundAdvise");
		//将此设置为“true”以强制代理 TargetSource 的公开目标类。 如果该目标类是一个接口，则会为给定的接口创建一个 JDK 代理。
		// 如果该目标类是任何其他类，则将为给定类创建 CGLIB 代理。
		beanNameAutoProxyCreator.setProxyTargetClass(true);
		return beanNameAutoProxyCreator;
	}


	/**
	 * 通过DefaultAdvisorAutoProxyCreator会直接去找所有Advisor类型的Bean，
	 *
	 * 根据Advisor中的PointCut和Advice信息，确定要代理的Bean以及代理逻辑。
	 *
	 */
//	@Bean
	public DefaultAdvisorAutoProxyCreator defaultAdvisorAutoProxyCreator() {
		DefaultAdvisorAutoProxyCreator defaultAdvisorAutoProxyCreator = new DefaultAdvisorAutoProxyCreator();
		return defaultAdvisorAutoProxyCreator;
	}

	/**
	 * DefaultPointcutAdvisor
	 * 它可以与任何切入点和建议类型一起使用，介绍除外。 通常不需要子类化此类，或实现自定义顾问。
	 *
	 * @return
	 */
//	@Bean
	public DefaultPointcutAdvisor defaultPointcutAdvisor(){
		NameMatchMethodPointcut pointcut = new NameMatchMethodPointcut();
		pointcut.addMethodName("test");
		DefaultPointcutAdvisor defaultPointcutAdvisor = new DefaultPointcutAdvisor();
		defaultPointcutAdvisor.setPointcut(pointcut);
//		defaultPointcutAdvisor.setAdvice(new MyAfterReturningAdvise());
		return defaultPointcutAdvisor;
	}



}
