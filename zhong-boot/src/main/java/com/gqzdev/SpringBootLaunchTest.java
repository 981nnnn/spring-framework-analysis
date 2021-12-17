package com.gqzdev;

import com.gqzdev.boot.MySpringApplication;
import com.gqzdev.boot.MySpringBootApplication;


/**
 * 模拟SpringBoot启动，内嵌tomcat
 */
@MySpringBootApplication
public class SpringBootLaunchTest {


	public static void main(String[] args) {
		MySpringApplication.run(SpringBootLaunchTest.class);
	}
}
