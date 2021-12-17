package com.analysis.enable.importBeanDefinitionRegistrar;

import org.springframework.stereotype.Component;

/**
 * @author ganquanzhong
 * @date 2021/11/03 14:37
 **/
@Component
public class HttpServer implements Server {

	@Override
	public void start() {
		System.out.println("HTTP 服务器启动中...");
	}

	@Override
	public void stop() {
		System.out.println("HTTP 服务器关闭中...");
	}
}
