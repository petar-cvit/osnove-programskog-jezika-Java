package hr.fer.zemris.java.p11.servleti;

import javax.servlet.ServletContext;
import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;
import javax.servlet.annotation.WebListener;

/**
 * Sets time represented in milliseconds as context attribute on initialization of context.
 * 
 * @author Petar Cvitanović
 *
 */
@WebListener
public class TimeServlet implements ServletContextListener{
	
	@Override
	public void contextInitialized(ServletContextEvent sce) {
		ServletContext context = sce.getServletContext();
		
		context.setAttribute("date", System.currentTimeMillis());
	}

	@Override
	public void contextDestroyed(ServletContextEvent sce) {
		
	}

}
