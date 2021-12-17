package com.gqzdev.aop;

import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

/**
 * @ClassName Test
 * @Description
 * @Author ganquanzhong
 * @Date2020/7/10 0:12
 * @Version
 **/
public class AopMain {

	public static void main(String[] args) {

		//读取xml中的bean配置
		ClassPathXmlApplicationContext ac = new ClassPathXmlApplicationContext("spring-aop.xml");

		//JavaConfig配置
//		AnnotationConfigApplicationContext ac = new AnnotationConfigApplicationContext(AppConfig.class);
//		Order order = (Order) ac.getBean("order");
		/**
		 * 过程
		 * 1. 解析AppConfig.class，得到扫描路径
		 * 2. 遍历扫描路径下的所有Java类，如果发现某个类上存在@Component、@Service等注解，那么Spring就把这个类记录下来，存在一个Map中，比如
		 * Map<String, Class>。（实际上，Spring源码中确实存在类似的这么一个Map，叫做BeanDefinitionMap）
		 * 3. Spring会根据某个规则生成当前类对应的beanName，作为key存入Map，当前类作为value
		 */

		EnhancedUser enhancedUser = (EnhancedUser) ac.getBean("enhancedUser");
		enhancedUser.test();
	}
}
