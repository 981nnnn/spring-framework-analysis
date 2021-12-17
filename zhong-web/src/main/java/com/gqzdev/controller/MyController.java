package com.gqzdev.controller;

import com.gqzdev.service.MyService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class MyController {  // Bean

	@Autowired
	private MyService zhouyuService;


	@GetMapping("/test")
	public String test() {
		return zhouyuService.test();
	}

}
