package hr.fer.zemris.java.hw05.db;

/**
 * Types of tokens.
 * 
 * @author Petar Cvitanović
 *
 */
public enum QueryTokenType {
	
	/**
	 * attribute name token
	 */
	ATRIBUTE,
	
	/**
	 * operator token
	 */
	OPERATOR,
	
	/**
	 * parentheses token
	 */
	SYMBOL,
	
	/**
	 * string literal token
	 */
	LITERAL,
	
	/**
	 * and keyword token
	 */
	AND,
	
	/**
	 * end of file token
	 */
	EOF;

}
