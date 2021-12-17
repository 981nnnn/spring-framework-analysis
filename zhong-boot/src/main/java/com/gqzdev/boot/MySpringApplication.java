package com.gqzdev.boot;


import org.apache.catalina.*;
import org.apache.catalina.connector.Connector;
import org.apache.catalina.core.StandardContext;
import org.apache.catalina.core.StandardEngine;
import org.apache.catalina.core.StandardHost;
import org.apache.catalina.startup.Tomcat;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.web.context.support.AnnotationConfigWebApplicationContext;
import org.springframework.web.servlet.DispatcherServlet;

/**
 *
 * Spring应用启动， 启动一个tomcat
 *
 * @author gqzdev
 * @date 2021/09/09 20:10
 **/
public class MySpringApplication {

	public static ConfigurableApplicationContext run(Class config) {

		AnnotationConfigWebApplicationContext applicationContext = new AnnotationConfigWebApplicationContext();

		applicationContext.register(config);
		applicationContext.refresh();

		startTomcat(applicationContext);

		return applicationContext;

	}

	/**
	 * 启动tomcat容器
	 */
	private static Tomcat startTomcat(AnnotationConfigWebApplicationContext applicationContext) {

		Tomcat tomcat = new Tomcat();

		Server server = tomcat.getServer();
		Service service = server.findService("Tomcat");

		//设置connector连接及端口
		Connector connector = new Connector();
		connector.setPort(8081);

		Engine engine = new StandardEngine();
		engine.setDefaultHost("localhost");

		Host host = new StandardHost();
		host.setName("localhost");

		String contextPath = "";
		Context context = new StandardContext();
		context.setPath(contextPath);
		context.addLifecycleListener(new Tomcat.FixContextListener());

		host.addChild(context);
		engine.addChild(host);

		service.setContainer(engine);
		service.addConnector(connector);


		tomcat.addServlet(contextPath, "dispatcher", new DispatcherServlet(applicationContext));
		context.addServletMappingDecoded("/*", "dispatcher");

		try {
			tomcat.start();
		} catch (LifecycleException e) {
			e.printStackTrace();
		}

//		Thread thread = new Thread(new Runnable() {
//			@Override
//			public void run() {
//				tomcat.getServer().await();
//			}
//		});
//		thread.setDaemon(false);
//		thread.start();

		return tomcat;
	}
}

