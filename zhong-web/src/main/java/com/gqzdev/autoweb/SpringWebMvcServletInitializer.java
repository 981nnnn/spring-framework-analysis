package com.gqzdev.autoweb;

import org.springframework.web.servlet.support.AbstractAnnotationConfigDispatcherServletInitializer;

/**
 * @author ganquanzhong
 * @date 2021/11/03 18:26
 **/
public class SpringWebMvcServletInitializer extends AbstractAnnotationConfigDispatcherServletInitializer {

	/**
	 * Specify {@code @Configuration} and/or {@code @Component} classes for the
	 * {@linkplain #createRootApplicationContext() root application context}.
	 *
	 * @return the configuration for the root application context, or {@code null}
	 * if creation and registration of a root context is not desired
	 */
	@Override
	protected Class<?>[] getRootConfigClasses() {
		return new Class[0];
	}

	/**
	 * Specify {@code @Configuration} and/or {@code @Component} classes for the
	 * {@linkplain #createServletApplicationContext() Servlet application context}.
	 *
	 * @return the configuration for the Servlet application context, or
	 * {@code null} if all configuration is specified through root config classes.
	 */
	//DispatcherServlet  配置Bean
	@Override
	protected Class<?>[] getServletConfigClasses() {
//		return new Class[0];

		return of(SpringWebMvcConfiguration.class);
	}


	/**
	 * Specify the servlet mapping(s) for the {@code DispatcherServlet} &mdash;
	 * for example {@code "/"}, {@code "/app"}, etc.
	 *
	 */
	@Override
	protected String[] getServletMappings() {
		//DispatcherServlet URL Pattern 映射
//		return new String[0];
		return of("/*");
	}

	/**
	 * 便利API ，减少new T[] 代码
	 * @param values
	 * @param <T>
	 * @return
	 */
	private static <T> T[] of(T... values){
		return values;
	}
}
