package com.xb.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * <p>todo</p>
 *
 * @Author xb
 * @Date 2021/12/17 17:00
 * @Version 1.0
 **/
@Service
public class UserService {
	@Autowired
	OrderService orderService;
}
