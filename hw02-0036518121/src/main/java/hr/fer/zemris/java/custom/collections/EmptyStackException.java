package hr.fer.zemris.java.custom.collections;

/**
 * Custom exception thrown when stack is empty.
 * 
 * @author Petar Cvitanović
 *
 */
public class EmptyStackException extends RuntimeException{
	private static final long serialVersionUID = 1L;
	
	/**
	 * Constructor that creates an instance of super class.
	 * 
	 * @param string
	 */
	public EmptyStackException(String s) {
		super(s);
	}
}
