package hr.fer.zemris.java.webserver.workers;

import java.io.IOException;

import hr.fer.zemris.java.webserver.IWebWorker;
import hr.fer.zemris.java.webserver.RequestContext;

/**
 * All given parameters are added in a table. As response sends html with table that shows all given parameters with their
 * names and values.
 * 
 * @author Petar Cvitanović
 *
 */
public class EchoParams implements IWebWorker{

	@Override
	public void processRequest(RequestContext context) throws Exception {
		context.setMimeType("text/html");
	
		try {
			context.write("<html>");
			context.write("<body>");
			
			context.write("<head><style>" + 
					"table, th, td {" + 
					"  border: 1px solid black;" + 
					"  border-collapse: collapse;" + 
					"}" + 
					"</style>" + 
					"</head>");
			
			context.write("<table>");
			
			for(String name : context.getParameterNames()) {
				context.write("<tr>");
				context.write("<th>" + name + "</th>");
				context.write("<th>" + context.getParameter(name) + "</th>");
				context.write("</tr>");
			}
		
			context.write("</table>");
			context.write("</body></html>");
		} catch(IOException ex) {
			ex.printStackTrace();
		}
	}

}
