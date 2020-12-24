package hr.fer.zemris.java.hw03.prob1;

/**
 * Custom lexer exception.
 * 
 * @author Petar Cvitanović
 *
 */
public class LexerException extends RuntimeException{

	private static final long serialVersionUID = 1L;
	
	/**
	 * Default constructor
	 */
	public LexerException() {
		super();
	}
	
	/**
	 * Constructor with message.
	 * 
	 * @param s
	 */
	public LexerException(String s) {
		super(s);
	}
}
