package com.analysis.enable.importBeanDefinitionRegistrar;

import org.springframework.beans.factory.support.BeanDefinitionBuilder;
import org.springframework.beans.factory.support.BeanDefinitionReaderUtils;
import org.springframework.beans.factory.support.BeanDefinitionRegistry;
import org.springframework.context.annotation.ImportBeanDefinitionRegistrar;
import org.springframework.core.type.AnnotationMetadata;

import java.util.Map;
import java.util.stream.Stream;

/**
 * 选择需要导入的类名称， 实现ImportBeanDefinitionRegistrar
 * <p>
 * 在AnnotationMetadata中寻找到bean命名，
 *
 * @author ganquanzhong
 * @date 2021/11/03 14:40
 **/
public class ServerImportBeanDefinitionRegistrar implements ImportBeanDefinitionRegistrar {

	/**
	 * 根据正在导入的@Configuration类的AnnotationMetadata，选择并返回应该导入的类的名称。
	 * <p>
	 * Select and return the names of which class(es) should be imported based on
	 * the {@link AnnotationMetadata} of the importing @{@link Configuration} class.
	 *
	 * @param importingClassMetadata 接口，该接口以一种不需要加载该类的形式定义对特定类的注释的抽象访问。
	 */
	public String[] selectImports(AnnotationMetadata importingClassMetadata) {
		//读取EnableServer中所有的属性方法，EnableServer中只有type()属性方法
		//其中key为属性方法的名称，value为属性方法的返回对象
		Map<String, Object> annotationAttributes = importingClassMetadata.getAnnotationAttributes(EnableServer.class.getName());

		//获取名为“type”的属性方法， 并且强制转化成Server.Type类型
		Server.Type type = (Server.Type) annotationAttributes.get("type");

		//导入的类名称数组
		String[] importClassNames = new String[0];

		switch (type) {
			case HTTP:
				importClassNames = new String[]{HttpServer.class.getName()};
				break;
			case FTP:
				importClassNames = new String[]{FtpServer.class.getName()};
				break;
		}

		return importClassNames;
	}

	/**
	 * 必要时根据导入的@Configuration类的给定注释元数据注册bean定义。
	 * <p>
	 * 注意，由于与@Configuration类处理相关的生命周期约束，BeanDefinitionRegistryPostProcessor类型可能不会在这里注册
	 * <p>
	 * Register bean definitions as necessary based on the given annotation metadata of
	 * the importing {@code @Configuration} class.
	 * <p>Note that {@link BeanDefinitionRegistryPostProcessor} types may <em>not</em> be
	 * registered here, due to lifecycle constraints related to {@code @Configuration}
	 * class processing.
	 *
	 * @param importingClassMetadata annotation metadata of the importing class  注释导入类的元数据
	 * @param registry               current bean definition registry 当前bean定义注册表
	 */
	@Override
	public void registerBeanDefinitions(AnnotationMetadata importingClassMetadata, BeanDefinitionRegistry registry) {
		ServerImportBeanDefinitionRegistrar importSelector = new ServerImportBeanDefinitionRegistrar();

		String[] selectedClassName = importSelector.selectImports(importingClassMetadata);

		//创建Bean定义，并注册

		Stream.of(selectedClassName)
				//转化为BeanDefinitionBuilder 对象
				.map(BeanDefinitionBuilder::genericBeanDefinition)
				//转化为BeanDefinition
				.map(BeanDefinitionBuilder::getBeanDefinition)
				.forEach(beanDefintion ->
						//注册BeanDefintion到BeanDefintionRegistry
						//使用生成的名称注册给定的bean定义，该名称在给定的bean工厂中是唯一的。
						BeanDefinitionReaderUtils.registerWithGeneratedName(beanDefintion, registry)
				);
	}
}
