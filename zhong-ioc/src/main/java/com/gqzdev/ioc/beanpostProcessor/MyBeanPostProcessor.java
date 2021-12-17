package com.gqzdev.ioc.beanpostProcessor;

import org.springframework.beans.BeansException;
import org.springframework.beans.factory.config.BeanPostProcessor;

/**
 * BeanPostProcessor是Spring IOC容器给我们提供的一个扩展接口
 *
 * @author gqzdev
 * @date 2021/10/08 16:38
 **/
public class MyBeanPostProcessor implements BeanPostProcessor {
	/**
	 * bean初始化方法调用前被调用
	 * <p>
	 * 在任何 bean 初始化回调（如 InitializingBean 的afterPropertiesSet或自定义初始化方法）之前，
	 * 将此 BeanPostProcessor 应用于给定的新 bean 实例。 bean 已经被填充了属性值。 返回的 bean 实例可能是原始实例的包装器。
	 * 默认实现按原样返回给定的bean 。
	 */
	@Override
	public Object postProcessBeforeInitialization(Object bean, String beanName) throws BeansException {
		System.out.println("MyBeanPostProcessor" + bean);
		System.out.println("MyBeanPostProcessor" + beanName);
		return BeanPostProcessor.super.postProcessBeforeInitialization(bean, beanName);
	}

	/**
	 * bean初始化方法调用后被调用
	 *
	 * @param bean     the new bean instance
	 * @param beanName the name of the bean
	 * @return
	 * @throws BeansException
	 */
	@Override
	public Object postProcessAfterInitialization(Object bean, String beanName) throws BeansException {
		System.out.println("MyBeanPostProcessor" + bean);
		System.out.println("MyBeanPostProcessor" + beanName);
		return BeanPostProcessor.super.postProcessAfterInitialization(bean, beanName);
	}
}
