package com.gqzdev.testautowired;

import org.springframework.context.annotation.ComponentScan;
import org.springframework.stereotype.Component;

/**
 * @author ganquanzhong
 * @date 2021/11/08 14:54
 **/
@Component
public class User {

	private String name;

	private String nickname;


	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getNickname() {
		return nickname;
	}

	public void setNickname(String nickname) {
		this.nickname = nickname;
	}

	@Override
	public String toString() {
		return "User{" +
				"name='" + name + '\'' +
				", nickname='" + nickname + '\'' +
				'}';
	}
}
