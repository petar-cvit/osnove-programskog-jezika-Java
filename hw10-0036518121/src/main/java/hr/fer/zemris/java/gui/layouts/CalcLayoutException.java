package hr.fer.zemris.java.gui.layouts;

/**
 * Custom exception.
 * 
 * @author Petar Cvitanović
 *
 */
public class CalcLayoutException extends RuntimeException{

	private static final long serialVersionUID = 1L;
	
	/**
	 * Default constructor.
	 */
	public CalcLayoutException() {
		super();
	}
	
	/**
	 * Constructor with exception message.
	 * 
	 * @param m
	 */
	public CalcLayoutException(String m) {
		super(m);
	}

}
