package hr.fer.zemris.java.custom.scripting.lexer;

/**
 * Custom Lexer exception.
 * 
 * @author Petar Cvitanović
 *
 */
public class LexerException extends RuntimeException{

	private static final long serialVersionUID = 1L;
	
	/**
	 * Default constructor.
	 */
	public LexerException() {
		super();
	}
	
	/**
	 * Constructor with string.
	 * 
	 * @param message
	 */
	public LexerException(String s) {
		super(s);
	}
}
