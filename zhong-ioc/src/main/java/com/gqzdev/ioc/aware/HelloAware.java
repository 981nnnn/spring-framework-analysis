package com.gqzdev.ioc.aware;

import org.springframework.beans.BeansException;
import org.springframework.beans.factory.*;
import org.springframework.beans.factory.config.BeanDefinition;
import org.springframework.beans.factory.config.BeanFactoryPostProcessor;
import org.springframework.beans.factory.config.BeanPostProcessor;
import org.springframework.beans.factory.config.ConfigurableListableBeanFactory;
import org.springframework.context.*;
import org.springframework.core.env.Environment;
import org.springframework.core.io.ResourceLoader;
import org.springframework.stereotype.Component;
import org.springframework.util.StringValueResolver;

import java.util.Arrays;

/**
 * @ClassName: HelloAware   Bean在IOC中的生命周期
 * @author: ganquanzhong
 * @date: 2020/7/10 11:40
 */
// 为了演示init-method 方法，将其用@Bean注解
@Component
public class HelloAware implements
		BeanNameAware,
		BeanClassLoaderAware,
		BeanFactoryAware,
		EnvironmentAware,
		EmbeddedValueResolverAware,
		ResourceLoaderAware,
		ApplicationEventPublisherAware,
		MessageSourceAware,
		ApplicationContextAware,
		BeanPostProcessor,
		InitializingBean,
		BeanFactoryPostProcessor {

	/**
	 * 实现BeanNameAware接口 ，可以获取当前BeanName
	 */
	private String beaName;

	/**
	 * 实现BeanClassLoaderAware接口， 可以获取加载当前bean的ClassLoader
	 */
	private ClassLoader classLoader;

	/**
	 * 实现BeanFactoryAware接口后，Spring 容器将会注入BeanFactory 的实例
	 */
	private BeanFactory beanFactory;

	/**
	 * 实现EnvironmentAware接口， Spring 容器将会注入 Environment 的实例
	 */
	private Environment environment;

	/**
	 * 实现EmbeddedValueResolverAware接口， Spring容器将会注入 StringValueResolver的实例
	 */
	private StringValueResolver stringValueResolver;

	/**
	 * 实现ResourceLoaderAware 接口， Spring容器将会注入 ResourceLoader的实例
	 */
	private ResourceLoader resourceLoader;

	/**
	 * 实现ApplicationEventPublisher 接口， Spring容器将会注入 ApplicationEventPublisher的实例
	 */
	private ApplicationEventPublisher applicationEventPublisher;

	/**
	 * 国际化操作
	 * 实现MessageSourceAware 接口 ，Spring容器将会注入 MessageSource的实例
	 */
	private MessageSource messageSource;

	/**
	 * 实现ApplicationContextAware 接口 ，Spring容器将会注入ApplicationContext的实例
	 */
	private ApplicationContext applicationContext;

	/**
	 * 在web工程中使用
	 */
	//private ServletContext servletContext;
	@Override
	public void setBeanName(String name) {
		this.beaName = name;
		System.out.println("1. BeanNameAware's setBeanName >>>" + beaName);
	}

	@Override
	public void setBeanClassLoader(ClassLoader classLoader) {
		this.classLoader = classLoader;
		System.out.println("2. BeanClassLoaderAware's setBeanClassLoader >>>" + classLoader);
	}

	@Override
	public void setBeanFactory(BeanFactory beanFactory) throws BeansException {
		this.beanFactory = beanFactory;
		System.out.println("3. BeanFactoryAware's setBeanFactory >>>" + beanFactory);
	}

	@Override
	public void setEnvironment(Environment environment) {
		this.environment = environment;
		System.out.println("4. EnvironmentAware's setEnvironment >>>" + environment);
	}

	/**
	 * 由任何希望收到StringValueResolver通知以解析嵌入定义值的对象实现的接口。
	 * 这是通过 ApplicationContextAware / BeanFactoryAware 接口的完整 ConfigurableBeanFactory 依赖项的替代方案。
	 *
	 * @param resolver
	 */
	@Override
	public void setEmbeddedValueResolver(StringValueResolver resolver) {
		this.stringValueResolver = resolver;
		System.out.println("5. EmbeddedValueResolverAware's setEmbeddedValueResolver >>>" + stringValueResolver);
	}

	@Override
	public void setResourceLoader(ResourceLoader resourceLoader) {
		this.resourceLoader = resourceLoader;
		System.out.println("6. ResourceLoaderAware's setResourceLoader (only applicable when running in an application context) >>>" + resourceLoader);
	}

	@Override
	public void setApplicationEventPublisher(ApplicationEventPublisher applicationEventPublisher) {
		this.applicationEventPublisher = applicationEventPublisher;
		System.out.println("7. ApplicationEventPublisherAware's setApplicationEventPublisher (only applicable when running in an application context) >>>" + messageSource);
	}

	@Override
	public void setMessageSource(MessageSource messageSource) {
		this.messageSource = messageSource;
		System.out.println("8. MessageSourceAware's setMessageSource (only applicable when running in an application context) >>>" + applicationEventPublisher);
	}

	@Override
	public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
		this.applicationContext = applicationContext;
		System.out.println("9. ApplicationContextAware's setApplicationContext (only applicable when running in an application context) >>>" + applicationContext);
	}

	/**
	 * 后置处理器：初始化前后进行处理工作   每个Bean都会进入改方法
	 * 实现 {@link BeanPostProcessor} 接口中的两个方法
	 * postProcessorBeforeInitialization,
	 * postProcessorAfterInitialization
	 */
	@Override
	public Object postProcessBeforeInitialization(Object bean, String beanName) throws BeansException {
		System.out.println("11.postProcessBeforeInitialization methods of BeanPostProcessors >>>" + beanName + "=>" + bean);
		return bean;
	}

	@Override
	public Object postProcessAfterInitialization(Object bean, String beanName) throws BeansException {
		System.out.println("14.postProcessAfterInitialization methods of BeanPostProcessors >>>" + beanName + "=>" + bean);
		return bean;
	}

	/**
	 * 初始化时
	 */
	@Override
	public void afterPropertiesSet() throws Exception {
		System.out.println("12.InitializingBean's afterPropertiesSet >>>自定义初始化方法，在init-method之前执行");
	}

	public void initMethod() {
		System.out.println("13.a custom init-method definition >>> 自定义init-method方法");
	}

	/**
	 * BeanFactoryPostProcessor是在spring容器加载了bean的定义文件之后，在bean实例化之前执行的。
	 * 接口方法的入参是ConfigurrableListableBeanFactory，使用该参数，可以获取到相关bean的定义信息
	 *
	 * <p>
	 *
	 *  spring中，有内置的一些BeanFactoryPostProcessor实现类，常用的有：
	 * 	org.springframework.beans.factory.config.PropertyPlaceholderConfigurer
	 *  org.springframework.beans.factory.config.PropertyOverrideConfigurer
	 *  org.springframework.beans.factory.config.CustomEditorConfigurer：用来注册自定义的属性编辑器

	 * </p>
	 *
	 * 实现BeanFactoryPostProcessor接口，中的postProcessorBeanFactory方法
	 * <p>
	 * 可以对BeanFactory进行操作，修改等
	 */
	@Override
	public void postProcessBeanFactory(ConfigurableListableBeanFactory beanFactory) throws BeansException {
		System.out.println("10.BeanFactoryPostProcessor...postProcessBeanFactory...");
		// 修改BeanDefinition
		BeanDefinition helloAwareBD = beanFactory.getBeanDefinition("helloAware");
		helloAwareBD.setInitMethodName("initMethod");
		System.out.println("\t initMethod >>>"+helloAwareBD);

		//返回工厂中定义的 bean 数量。
		//不考虑该工厂可能参与的任何层次结构，并忽略通过 bean 定义以外的其他方式注册的任何单例 bean。
		int count = beanFactory.getBeanDefinitionCount();
		String[] names = beanFactory.getBeanDefinitionNames();
		System.out.println("\t 当前BeanFactory中有" + count + " 个Bean " + Arrays.asList(names));
	}

	/**
	 * 这些xxxAware接口，组成BeanFactory的生命周期
	 * <p>
	 * 例如；拿到了BeanFactory，可以获取Spring容器中所有的Bean，来完成你的业务操作
	 */
	public void testAware() {
		// 从BeanFactory中获取hello，并调用里面的方法
		Hello hello = beanFactory.getBean("hello", Hello.class);

		hello.say("gqzdev");
	}


}
