package com.gqzdev;

import com.gqzdev.config.WebConfig;
import org.springframework.web.WebApplicationInitializer;
import org.springframework.web.context.support.AnnotationConfigWebApplicationContext;
import org.springframework.web.servlet.DispatcherServlet;

import javax.servlet.ServletContext;
import javax.servlet.ServletRegistration;

/**
 * @author gqzdev
 */
public class MyWebApplicationInitializer implements WebApplicationInitializer {

	/**
	 *
	 * @param servletContext the {@code ServletContext} to initialize
	 */
	@Override
	public void onStartup(ServletContext servletContext) {

		// Load Spring web application configuration
		AnnotationConfigWebApplicationContext context = new AnnotationConfigWebApplicationContext();
		context.register(WebConfig.class);

		// Create and register the DispatcherServlet
		DispatcherServlet servlet = new DispatcherServlet(context);
		ServletRegistration.Dynamic registration = servletContext.addServlet("app", servlet);

		registration.setLoadOnStartup(1);
		registration.addMapping("/app/*");
	}
}
