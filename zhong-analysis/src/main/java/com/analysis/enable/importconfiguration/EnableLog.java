package com.analysis.enable.importconfiguration;

import org.springframework.context.annotation.Import;

import java.lang.annotation.*;

/**
 * 开启log注解
 */

@Target(ElementType.TYPE)
@Retention(RetentionPolicy.RUNTIME)
@Documented
@Import(EnableLogConfiguration.class) //导入EnableLogConfiguration配置
public @interface EnableLog {
}
