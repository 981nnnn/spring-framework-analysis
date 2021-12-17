package com.analysis.enable.importselector;

import org.springframework.context.annotation.ImportSelector;
import org.springframework.core.type.AnnotationMetadata;

import java.util.Map;

/**
 * 选择需要导入的类名称， @Import会根据名称创建
 *
 * @author ganquanzhong
 * @date 2021/11/03 14:40
 **/
public class ServerImportSelector implements ImportSelector {

	/**
	 * 根据正在导入的@Configuration类的AnnotationMetadata，选择并返回应该导入的类的名称。
	 *
	 * Select and return the names of which class(es) should be imported based on
	 * the {@link AnnotationMetadata} of the importing @{@link Configuration} class.
	 *
	 * @param importingClassMetadata  接口，该接口以一种不需要加载该类的形式定义对特定类的注释的抽象访问。
	 */
	@Override
	public String[] selectImports(AnnotationMetadata importingClassMetadata) {
		//读取EnableServer中所有的属性方法，EnableServer中只有type()属性方法
		//其中key为属性方法的名称，value为属性方法的返回对象
		Map<String, Object> annotationAttributes = importingClassMetadata.getAnnotationAttributes(EnableServer.class.getName());

		//获取名为“type”的属性方法， 并且强制转化成Server.Type类型
		Server.Type type = (Server.Type) annotationAttributes.get("type");

		//导入的类名称数组
		String[] importClassNames = new String[0];

		switch (type){
			case HTTP:
				importClassNames = new String[]{HttpServer.class.getName()};
				break;
			case FTP:
				importClassNames = new String[]{FtpServer.class.getName()};
				break;
		}

		return importClassNames;
	}
}
