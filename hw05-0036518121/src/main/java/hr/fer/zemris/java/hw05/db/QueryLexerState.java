package hr.fer.zemris.java.hw05.db;

/**
 * Lexer's states.
 * 
 * @author Petar Cvitanović
 *
 */
public enum QueryLexerState {
	
	/**
	 * basic state
	 */
	BASIC,
	
	/**
	 * state inside parentheses
	 */
	LITERAL;
}
