package hr.fer.zemris.java.tecaj_13.dao;

/**
 * Custom exception.
 * 
 * @author Petar Cvitanović
 *
 */
public class DAOException extends RuntimeException {

	private static final long serialVersionUID = 1L;

	/**
	 * Constructor.
	 * 
	 * @param message
	 * @param cause
	 */
	public DAOException(String message, Throwable cause) {
		super(message, cause);
	}

	/**
	 * Constructor.
	 * 
	 * @param message
	 */
	public DAOException(String message) {
		super(message);
	}
}