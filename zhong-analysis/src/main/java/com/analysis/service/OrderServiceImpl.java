package com.analysis.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component("orderService")
public class OrderServiceImpl implements OrderService {

	@Autowired
	private UserService userService;

	public void test() {
		System.out.println("test");
	}
}
