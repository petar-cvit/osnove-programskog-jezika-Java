package hr.fer.zemris.java.webserver.workers;

import hr.fer.zemris.java.webserver.IWebWorker;
import hr.fer.zemris.java.webserver.RequestContext;

/**
 * Checks whether or not background colour is set, and is it in valid format. Colour has to
 * be represented as six hexadecimal digits. If it is, that colour is put in persistent parameters,
 * and user is informed about that change. If colout isn't valid user is also informed about that.
 * 
 * @author Petar Cvitanović
 *
 */
public class BgColorWorker implements IWebWorker {

	@Override
	public void processRequest(RequestContext context) throws Exception {
		String color = context.getParameter("bgcolor");
		
		if(color.matches("^[0-9A-F]+$") && color.length() == 6) {
			context.setPersistentParameter("bgcolor", color);
			context.setPersistentParameter("message", "Color has been updated!");
		} else {
			context.setPersistentParameter("message", "Invalid color has been given!g");
		}
		
		context.getDispatcher().dispatchRequest("/private/pages/updateColor.smscr");
	}

}
