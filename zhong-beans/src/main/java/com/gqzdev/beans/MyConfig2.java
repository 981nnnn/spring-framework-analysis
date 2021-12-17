package com.gqzdev.beans;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class MyConfig2 {

	@Bean
	public Person user() {
		return new Person();
	}

	@Bean
	public Role role() {
		return new Role();
	}
}