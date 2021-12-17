package com.gqzdev.autoweb;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * @author ganquanzhong
 * @date 2021/11/03 18:13
 **/

@Controller
public class HelloWorldController {

	@RequestMapping
	@ResponseBody
	public String helloWorld(){
		return "Hello World!!!";
	}
}
