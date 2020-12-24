package hr.fer.zemris.java.hw06.shell.parser;

/**
 * Token class represents lexer's token with it's type and value.
 * 
 * @author Petar Cvitanović
 *
 */
public class Token {

	/**
	 * token value
	 */
	private String value;
	
	/**
	 * token type
	 */
	private TokenType type;

	/**
	 * Constructor with value and type.
	 * 
	 * @param value
	 * @param type
	 */
	public Token(String value, TokenType type) {
		if(value == null || type == null) {
			throw new NullPointerException();
		}
		
		this.value = value;
		this.type = type;
	}

	/**
	 * Token value getter.
	 * 
	 * @return value
	 */
	public String getValue() {
		return value;
	}

	/**
	 * Token type getter.
	 * 
	 * @return type
	 */
	public TokenType getType() {
		return type;
	}
	
}
