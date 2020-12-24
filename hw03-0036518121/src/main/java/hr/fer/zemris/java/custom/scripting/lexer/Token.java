package hr.fer.zemris.java.custom.scripting.lexer;

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
	 * Constructor with type and value.
	 * 
	 * @throws NullPointerException
	 * 				if type or value are null
	 * 
	 * @param type
	 * @param value
	 */
	public Token(TokenType type, Object value) {
		if(value == null || type == null) {
			throw new NullPointerException();
		}
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
	 * Type getter.
	 * 
	 * @return type of token
	 */
	public TokenType getType() {
		return type;
	}
}
