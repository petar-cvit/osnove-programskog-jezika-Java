package hr.fer.zemris.java.hw03.prob1;

/**
 * Types of tokens
 * 
 * @author Petar Cvitanović
 *
 */
public enum TokenType {
	
	/**
	 * used as last token
	 */
	EOF, 
	
	/**
	 * used to hold strings from text
	 */
	WORD, 
	
	/**
	 * used to hold numbers from text
	 */
	NUMBER, 
	
	/**
	 * used for '#' character
	 */
	SYMBOL;
}
