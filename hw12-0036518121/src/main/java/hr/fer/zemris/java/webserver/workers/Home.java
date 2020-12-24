package hr.fer.zemris.java.webserver.workers;

import hr.fer.zemris.java.webserver.IWebWorker;
import hr.fer.zemris.java.webserver.RequestContext;

/**
 * Home page that has all links to workers and smart scripts. Also user can change background colour in dropdown menu.
 * User can input operands, submit them, and be displayed page that shows him operands and sum.
 * 
 * @author Petar Cvitanović
 *
 */
public class Home implements IWebWorker{

	@Override
	public void processRequest(RequestContext context) throws Exception {
		String color = context.getPersistentParameter("bgcolor");
		if(color != null) {
			context.setTemporaryParameter("background", color);
		}
		
		context.getDispatcher().dispatchRequest("/private/pages/home.smscr");
	}
	
}
