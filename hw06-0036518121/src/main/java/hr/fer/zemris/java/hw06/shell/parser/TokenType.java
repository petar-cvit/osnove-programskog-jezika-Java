package hr.fer.zemris.java.hw06.shell.parser;

/**
 * Types of tokens.
 * 
 * @author Petar Cvitanović
 *
 */
public enum TokenType {

	/**
	 * text token
	 */
	TEXT,
	
	/**
	 * quotation marks token
	 */
	SYMBOL,
	
	/**
	 * end of file token
	 */
	EOF;
}
