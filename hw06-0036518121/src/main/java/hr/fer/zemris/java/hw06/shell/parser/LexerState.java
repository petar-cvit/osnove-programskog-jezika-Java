package hr.fer.zemris.java.hw06.shell.parser;

/**
 * Lexer's states.
 * 
 * @author Petar Cvitanović
 */
public enum LexerState {
	
	/**
	 * for text outside quotes
	 */
	BASIC,
	
	/**
	 * for text inside quotation marks
	 */
	QUOTATION;

}
