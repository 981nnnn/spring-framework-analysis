package com.analysis.enable.importBeanDefinitionRegistrar;

import org.springframework.context.annotation.Import;

import java.lang.annotation.*;

@Target(ElementType.TYPE)
@Retention(RetentionPolicy.RUNTIME)
@Documented
@Import(ServerImportBeanDefinitionRegistrar.class) //使用ImportBeanDefinitionRegistrar方式
public @interface EnableServer {

	/**
	 * 设置服务器类型
	 */
	Server.Type type();
}
