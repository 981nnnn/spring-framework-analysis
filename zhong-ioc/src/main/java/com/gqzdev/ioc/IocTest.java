package com.gqzdev.ioc;

import com.gqzdev.ioc.autowire.ClassRoom;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

import java.util.Arrays;

/**
 * @author gqzdev
 * @date 2021/10/08 16:45
 **/
public class IocTest {

	public static void main(String[] args) {


		AnnotationConfigApplicationContext context = new AnnotationConfigApplicationContext(AppConfig.class);


		Arrays.stream(context.getBeanDefinitionNames()).forEach(s -> System.out.println(s));


		ClassRoom classRoom = context.getBean("classRoom", ClassRoom.class);

		System.out.println(classRoom.toString());

	}
}
