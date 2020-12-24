package hr.fer.zemris.java.webserver;

/**
 * Dispathes request with dispatch request method.
 * 
 * @author Petar Cvitanović
 *
 */
public interface IDispatcher {
	
	/**
	 * Takes requested url and dispatches request.
	 * 
	 * @param urlPath
	 * @throws Exception
	 */
	void dispatchRequest(String urlPath) throws Exception;
}
