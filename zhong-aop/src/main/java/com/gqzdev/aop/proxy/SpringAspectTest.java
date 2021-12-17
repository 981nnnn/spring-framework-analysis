package com.gqzdev.aop.proxy;

import com.gqzdev.aop.proxy.jdk.UserInterface;
import com.gqzdev.aop.proxy.jdk.UserService;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

import java.util.Arrays;

/**
 * https://www.processon.com/view/link/5faa4ccce0b34d7a1aa2a9a5
 *
 * @author gqzdev
 * @date 2021/10/09 17:07
 **/
public class SpringAspectTest {


	public static void main(String[] args) {


		AnnotationConfigApplicationContext context = new AnnotationConfigApplicationContext(AppConfig.class);

		Arrays.stream(context.getBeanDefinitionNames()).forEach(System.out::println);

		UserInterface userServiceProxy = (UserInterface)context.getBean(UserInterface.class);

		userServiceProxy.test();

	}
}
