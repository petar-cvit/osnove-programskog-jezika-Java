package hr.fer.zemris.java.custom.scripting.lexer;

/**
 * Lexer's states.
 * 
 * @author Petar Cvitanović
 *
 */
public enum LexerState {
	/**
	 * lexer is out of tags
	 */
	BASIC,
	
	/**
	 * lexer is in tag
	 */
	TAG;
}
