package hr.fer.zemris.java.hw03.prob1;

/**
 * Lexer states.
 * 
 * @author Petar Cvitanović
 *
 */
public enum LexerState {
	/**
	 * lexer is outside of text delimited by '#' characters
	 */
	BASIC, 
	
	/**
	 * lexer is inside of text delimited by '#' characters
	 */
	EXTENDED;
}
