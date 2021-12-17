package com.analysis.enable.importconfiguration;

import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.context.annotation.Configuration;

/**
 * @Enable 模块驱动
 *
 * @author ganquanzhong
 * @date 2021/11/03 14:24
 **/

@EnableLog
@Configuration
public class EnableLogBootstrap {

	public static void main(String[] args) {

		AnnotationConfigApplicationContext context = new AnnotationConfigApplicationContext();
		context.register(EnableLogBootstrap.class);
		context.refresh();


	}
}
