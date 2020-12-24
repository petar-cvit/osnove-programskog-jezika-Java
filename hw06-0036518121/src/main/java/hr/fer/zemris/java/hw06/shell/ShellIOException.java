package hr.fer.zemris.java.hw06.shell;

/**
 * Custom exception.
 * 
 * @author Petar Cvitanović
 *
 */
public class ShellIOException extends RuntimeException {

	/**
	 * serial version UID
	 */
	private static final long serialVersionUID = 1L;
	
	/**
	 * Default constructor.
	 */
	public ShellIOException() {
		super();
	}
	
	/**
	 * Constructor with message.
	 * 
	 * @param message
	 */
	public ShellIOException(String m) {
		super(m);
	}

}
