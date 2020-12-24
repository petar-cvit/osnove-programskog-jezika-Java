package hr.fer.zemris.java.hw05.db;

/**
 * Represents lexer's token.
 * 
 * @author Petar Cvitanović
 *
 */
public class QueryToken {
	
	/**
	 * token value
	 */
	private String value;
	
	/**
	 * token type
	 */
	private QueryTokenType type;
	
	/**
	 * Constructor with value and type.
	 * 
	 * @throws NullPointerException
	 * 				if given type or value are null.
	 * 
	 * @param value
	 * @param type
	 */
	public QueryToken(String value, QueryTokenType type) {
		if(type == null || value == null) {
			throw new NullPointerException("Null Token!");
		}
		
		this.value = value;
		this.type = type;
	}

	/**
	 * Value getter.
	 * 
	 * @return token value
	 */
	public String getValue() {
		return value;
	}

	/**
	 * Type getter.
	 * 
	 * @return type of token
	 */
	public QueryTokenType getType() {
		return type;
	}

}
