package com.analysis.config;

import com.analysis.bean.Computer;
import com.analysis.bean.Phone;
import org.springframework.beans.factory.support.BeanDefinitionRegistry;
import org.springframework.beans.factory.support.RootBeanDefinition;
import org.springframework.context.MessageSource;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;
import org.springframework.context.annotation.ImportBeanDefinitionRegistrar;
import org.springframework.context.support.ResourceBundleMessageSource;
import org.springframework.core.type.AnnotationMetadata;


//通过Import注解导入, 其中直接写入Class在容器中是全限定类名
@Import({Computer.class,MyImportRegistrar.class})
//只扫描到service包下面
@ComponentScan("com.analysis.service")
@Configuration
public class AppConfig {

	/**
	 * 先定义一个MessageSource
	 *
	 * 有了这个Bean，你可以在你任意想要进行国际化的地方使用该MessageSource。
	 * 同时，因为ApplicationContext也拥有国家化的功能，所以可以直接这么用
	 */
	@Bean
	public MessageSource messageSource(){
		ResourceBundleMessageSource messageSource = new ResourceBundleMessageSource();
		messageSource.setBasename("messages");
		return messageSource;
	}
}


/**
 *
 *	BeanDefinitionRegistry: Bean定义信息注册中心， 图纸中心
 *		它里面都是BeanDefinition： Bean定义信息
 */
class MyImportRegistrar implements ImportBeanDefinitionRegistrar{

	@Override
	public void registerBeanDefinitions(AnnotationMetadata importingClassMetadata, BeanDefinitionRegistry registry) {

		/**
		 *	BeanDefinition
		 *	自己创建一个BeanDefinition
		 *	可以声明定义信息，包括我需要自动装配什么？
		 *	Spring 这个实例的类型，名字
		 */
		RootBeanDefinition phoneDefinition = new RootBeanDefinition();
		phoneDefinition.setBeanClass(Phone.class);

		//通过BeanDefinition注册中心添加
		registry.registerBeanDefinition("xiaomi_phone",phoneDefinition);
	}
}