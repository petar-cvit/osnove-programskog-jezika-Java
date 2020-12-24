package hr.fer.zemris.java.webserver;

/**
 * Creates response with processRequest method.
 * 
 * @author Petar Cvitanović
 *
 */
public interface IWebWorker {

	/**
	 * Creates response.
	 * 
	 * @param context
	 * @throws Exception
	 */
	public void processRequest(RequestContext context) throws Exception;

}