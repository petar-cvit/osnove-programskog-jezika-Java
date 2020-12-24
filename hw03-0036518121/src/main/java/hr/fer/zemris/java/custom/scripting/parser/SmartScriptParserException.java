package hr.fer.zemris.java.custom.scripting.parser;

/**
 * Custom parser exception class.
 * 
 * @author Petar Cvitanović
 *
 */
public class SmartScriptParserException extends RuntimeException {

	private static final long serialVersionUID = 1L;

	/**
	 * Default constructor
	 */
	public SmartScriptParserException() {
		super();
	}
	
	/**
	 * Constructor with string
	 * 
	 * @param s
	 */
	public SmartScriptParserException(String s) {
		super(s);
	}
}
