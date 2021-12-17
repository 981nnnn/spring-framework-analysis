package com.analysis;


import com.analysis.config.AppConfig;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.core.env.MutablePropertySources;

import java.util.Map;

public class MainTest {

	public static void main(String[] args) {

		AnnotationConfigApplicationContext applicationContext = new AnnotationConfigApplicationContext(AppConfig.class);
//		OrderService orderService = (OrderService) applicationContext.getBean("orderService");
//		orderService.test();

//		String message = applicationContext.getMessage("test", null, new Locale("en_CN"));
//		System.out.println(message);

		//查看applicationContext中所有的Bean
		String[] names = applicationContext.getBeanDefinitionNames();
		for (String name : names) {
			System.out.println(name);
		}

		/**
		 * 获取运行时环境
		 */
		Map<String, Object> systemEnvironment = applicationContext.getEnvironment().getSystemEnvironment();
		System.out.println(systemEnvironment);
		System.out.println("=======");

		Map<String, Object> systemProperties = applicationContext.getEnvironment().getSystemProperties();
		System.out.println(systemProperties);
		System.out.println("=======");

		MutablePropertySources propertySources = applicationContext.getEnvironment().getPropertySources();
		System.out.println(propertySources);
		System.out.println("=======");



	}
}
