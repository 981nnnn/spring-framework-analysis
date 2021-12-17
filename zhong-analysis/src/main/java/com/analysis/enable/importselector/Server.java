package com.analysis.enable.importselector;


public interface Server {

	void start();


	void stop();


	enum Type{
		HTTP, //http服务器
		FTP //ftp服务器
	}
}
