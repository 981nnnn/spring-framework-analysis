package com.analysis.enable.importBeanDefinitionRegistrar;

import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.context.annotation.Configuration;

/**
 * @author ganquanzhong
 * @date 2021/11/03 14:47
 **/
@Configuration
@EnableServer(type = Server.Type.FTP)
public class EnableServerBootstarp {

	public static void main(String[] args) {

		AnnotationConfigApplicationContext context = new AnnotationConfigApplicationContext();

		context.register(EnableServerBootstarp.class);
		context.refresh();

		//获取Server Bean对象， 实际应为 HttpServer
		Server bean = context.getBean(Server.class);

		bean.start();

		bean.stop();
	}
}
