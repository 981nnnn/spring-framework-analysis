package com.gqzdev.beans;

import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;

@Import(Role.class)
@Configuration
public class MyConfig {
}