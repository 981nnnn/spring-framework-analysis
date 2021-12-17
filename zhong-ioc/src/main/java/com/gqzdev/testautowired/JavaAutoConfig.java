package com.gqzdev.testautowired;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;

/**
 * @author ganquanzhong
 * @date 2021/11/08 15:20
 **/
@Configuration
@ComponentScan("com.gqzdev.testautowired")
public class JavaAutoConfig {

	private User user;

	@Autowired
	public void getUser(User user){
		user.setName("123");
		user.setNickname("jjj");
		this.user = user;
	}

}
