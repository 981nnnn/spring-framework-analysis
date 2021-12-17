package com.gqzdev.aop;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.Signature;
import org.aspectj.lang.annotation.After;
import org.aspectj.lang.annotation.AfterReturning;
import org.aspectj.lang.annotation.AfterThrowing;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.context.annotation.EnableAspectJAutoProxy;
import org.springframework.stereotype.Component;

/**
 * @ClassName AspectJUser
 * @Description spring的ioc 容器中默认都是原生对象，
 * 只有通过aop增强的对象才是代理对象。 配置了aop的类或者类中方法上有@Transactional注解的（因为@Transactional注解的原理就是基于aop的）
 * @Author ganquanzhong
 * @Date2020/8/4 23:15
 * @Version
 **/
@Component
@Aspect
@EnableAspectJAutoProxy //开启Aspect切面代理
public class AspectJUser {

	/**
	 * 使用@Pointcut注解  封装切点表达式
	 */
	@Pointcut(" execution(* com.gqzdev.aop.*.*(..)) ")
	public void test() {
	}

	/**
	 * 前置通知
	 * @param joinPoint  JoinPoint对象封装了SpringAop中切面方法的信息,在切面方法中添加JoinPoint参数,
	 *                      <br>就可以获取到封装了该方法信息的JoinPoint对象.常用api
	 * <pre>
	 * 	Signature getSignature();	获取封装了署名信息的对象,在该对象中可以获取到目标方法名,所属类的Class等信息<br>
	 * 	Object[] getArgs();	获取传入目标方法的参数对象<br>
	 * 	Object getTarget();	获取被代理的对象<br>
	 * 	Object getThis();	获取代理对象   <br>
	 * </pre>
	 */
	@Before("test()")
	public void beforeTest(JoinPoint joinPoint) {
		//getTarget获取真正的对象
		Object target = joinPoint.getTarget();
		System.out.println("前置通知 beforeTest");
	}

	/**
	 * 后置通知
	 */
	@After("test()")
	public void after() {
		System.out.println("后置通知 afterTest");
	}

	/**
	 * 环绕通知
	 * @param p ProceedingJoinPoint对象是JoinPoint的子接口,该对象只用在@Around的切面方法中,
	 * 添加了
	 * <pre>
	 *              Object proceed() throws Throwable //执行目标方法
	 * 				Object proceed(Object[] var1) throws Throwable //传入的新的参数去执行目标方法
	 * </pre>
	 */
	@Around("test()")
	public Object aroundTest(ProceedingJoinPoint p) {
		Signature signature = p.getSignature();
		System.out.println("环绕通知 around before");
		Object o = null;
		try {
			//执行目标方法
			o = p.proceed();
		} catch (Throwable e) {
			e.printStackTrace();
		}
		System.out.println("环绕通知 around after");
		return o;
	}

	/**
	 * 返回通知
	 */
	@AfterReturning("test()")
	public void afterReturning() {
		System.out.println("返回通知 after-return");
	}

	/**
	 * 异常通知
	 */
	@AfterThrowing("test()")
	public void afterThrowing() {
		System.out.println("异常通知 after-throwing");
	}

}
