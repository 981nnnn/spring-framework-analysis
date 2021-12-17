package com.gqzdev;

import org.springframework.web.context.WebApplicationContext;
import org.springframework.web.servlet.support.AbstractDispatcherServletInitializer;

/**
 * 实现的是Spring XML配置驱动
 *
 * @author ganquanzhong
 * @date 2021/11/03 18:05
 **/
public class MyWebAppInitializer extends AbstractDispatcherServletInitializer {



	@Override
	protected WebApplicationContext createRootApplicationContext() {
		return null;
	}


	@Override
	protected WebApplicationContext createServletApplicationContext() {
		return null;
	}

	/**
	 * Specify the servlet mapping(s) for the {@code DispatcherServlet} &mdash;
	 * for example {@code "/"}, {@code "/app"}, etc.
	 *
	 */
	@Override
	protected String[] getServletMappings() {
		return new String[]{"/"};
	}
}
