package com.analysis.enable.importselector;

import org.springframework.stereotype.Component;

/**
 * @author ganquanzhong
 * @date 2021/11/03 14:37
 **/
@Component
public class FtpServer implements Server{

	@Override
	public void start() {
		System.out.println("Ftp 服务器启动中...");
	}

	@Override
	public void stop() {
		System.out.println("Ftp 服务器关闭中...");
	}
}
