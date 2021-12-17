package com.gqzdev.beans;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.stereotype.Controller;
import org.springframework.stereotype.Repository;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RestController;

import java.time.Period;

/**
 * @author gqzdev
 * @date 2021/07/10 10:19
 **/

//@Repository
//@Service
//@Controller
//@RestController

@Configuration
public class JavaConfig {

	@Bean
	public Person person(){
		return new Person("gqzdev",18);
	}
}
