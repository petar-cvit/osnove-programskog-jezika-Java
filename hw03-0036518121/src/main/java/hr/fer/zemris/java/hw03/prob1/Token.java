package hr.fer.zemris.java.hw03.prob1;

/**
 * Token class holds part of tokenized text as value and type of token.
 * 
 * @author Petar Cvitanović
 *
 */
public class Token {

	/**
	 * type of token
	 */
	private TokenType type;
	
	/**
	 * token value
	 */
	private Object value;
	
	/**
	 * Constructor with token type and token value.
	 * 
	 * @param type
	 * @param value
	 */
	public Token(TokenType type, Object value) {
		this.type = type;
		this.value = value;
	}
	
	/**
	 * Value getter.
	 * 
	 * @return token value
	 */
	public Object getValue() {
		return value;
	}
	
	/**
	 * Type getter
	 * 
	 * @return type of token
	 */
	public TokenType getType() {
		return type;
	}
	
	/**
	 * Value setter.
	 * 
	 * @param value to set for token value
	 */
	public void setValue(Object value) {
		this.value = value;
	}
	
	/**
	 * Type setter
	 * 
	 * @param type to set for token type
	 */
	public void setType(TokenType type) {
		this.type = type;
	}
}
