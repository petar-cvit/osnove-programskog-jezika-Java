package hr.fer.zemris.java.custom.scripting.lexer;

/**
 * Types of tokens.
 * 
 * @author Petar Cvitnaović
 *
 */
public enum TokenType {
	
	/**
	 * Used for variables
	 */
	VARIABLE,
	
	/**
	 * Used for integers
	 */
	INTEGER,
	
	/**
	 * Used for doubles
	 */
	DOUBLE,
	
	/**
	 * Used for strings
	 */
	STRING,
	
	/**
	 * Used for function names
	 */
	FUNCTION,
	
	/**
	 * Used for operators
	 */
	OPERATOR,
	
	/**
	 * Used for tag names
	 */
	TAG,
	
	/**
	 * Used for symbol to go in and get out of tags
	 */
	SYMBOL,
	
	/**
	 * Used as last token
	 */
	EOF;
}
