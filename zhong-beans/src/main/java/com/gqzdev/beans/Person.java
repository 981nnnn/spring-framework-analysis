package com.gqzdev.beans;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * Person实体
 *
 * @author gqzdev
 * @date 2021/07/10 10:11
 **/
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Person {

	private String name;

	private int age;
}
