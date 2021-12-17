package com.analysis.enable.importselector;

import org.springframework.context.annotation.Import;

import java.lang.annotation.*;

@Target(ElementType.TYPE)
@Retention(RetentionPolicy.RUNTIME)
@Documented
@Import(ServerImportSelector.class) //导入ServerImportSelector
public @interface EnableServer {

	/**
	 * 设置服务器类型
	 */
	Server.Type type();
}
