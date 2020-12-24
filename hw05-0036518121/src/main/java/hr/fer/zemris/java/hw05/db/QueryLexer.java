package hr.fer.zemris.java.hw05.db;

/**
 * Returns given text divided into tokens. Lexer has two states. Basic state if is reads something
 * outside of parentheses, and special state if it reads something inside parentheses.
 * 
 * @author Petar Cvitanović
 *
 */
public class QueryLexer {
	
	/**
	 * text that has to be tokenized
	 */
	private char[] data;
	
	/**
	 * last returned token
	 */
	private QueryToken token;
	
	/**
	 * index of the current character
	 */
	private int index;
	
	/**
	 * lexer's state
	 */
	private QueryLexerState state;
	
	/**
	 * Constructor with data that has to be tokenized. Sets state to basic.
	 * 
	 * @param data
	 */
	public QueryLexer(String data) {
		this.data = data.toCharArray();
		this.token = null;
		this.index = 0;
		this.state = QueryLexerState.BASIC;
	}
	
	/**
	 * Returns next generated token from data. As last token returns end of file token.
	 * 
	 * @throws IllegalArgumentException
	 * 				if method is called after the end of file token
	 * 
	 * @return next token
	 */
	public QueryToken nextToken() {
		skipSpaces();
		
		if(token != null && token.getType().equals(QueryTokenType.EOF)) {
			throw new IllegalArgumentException("No more tokens!");
			
		} else if(index == data.length) {
			 token = new QueryToken("End od file", QueryTokenType.EOF);
			 return token;
		}
		
		String tokenValue = "";
		QueryTokenType tokenType = null;
		
		if(state == QueryLexerState.LITERAL) {
			if(data[index] == '\"') {
				tokenValue = "\"";
				tokenType = QueryTokenType.SYMBOL;
				index++;
			
			} else {
				while(index < data.length && data[index] != '\"') {
					if(!(Character.isAlphabetic(data[index]) ||
							Character.isDigit(data[index]) ||
							Character.isWhitespace(data[index]) ||
							data[index] == '*')) {
						throw new IllegalArgumentException("Character " + data[index] + " illegal!");
					}
					
					tokenValue = tokenValue.concat(Character.toString(data[index++]));
				}
				
				tokenType = QueryTokenType.LITERAL;
			}

		} else {
			
			if(Character.toString(data[index]).toLowerCase().equals("a") &&
					Character.toString(data[index + 1]).toLowerCase().equals("n") &&
					Character.toString(data[index + 2]).toLowerCase().equals("d")) {
				tokenValue = "AND";
				tokenType = QueryTokenType.AND;
				
				index += 3;
			
			}  else if(data[index] == 'L' &&
					data[index + 1] == 'I' &&
					data[index + 2] == 'K' &&
					data[index + 3] == 'E') {
				tokenValue = "LIKE";
				tokenType = QueryTokenType.OPERATOR;
				
				index += 4;
			
			} else if(Character.isAlphabetic(data[index])) {
				while(Character.isAlphabetic(data[index])) {
					tokenValue = tokenValue.concat(Character.toString(data[index++]));
				}
				tokenType = QueryTokenType.ATRIBUTE;
				
			} else if("<>!=".indexOf(data[index]) != -1 && data[index + 1] == '=') {
				tokenValue = Character.toString(data[index]) + Character.toString(data[index + 1]);
				tokenType = QueryTokenType.OPERATOR;
				
				index += 2;
				
			} else if("<>!=".indexOf(data[index]) != -1) {
				tokenValue = Character.toString(data[index++]);
				tokenType = QueryTokenType.OPERATOR;
				
			} else if(data[index] == '\"') {
				tokenValue = "\"";
				tokenType = QueryTokenType.SYMBOL;
				index++;
				
			} else {
				throw new IllegalArgumentException("Illegal charcter: "+data[index]);
			}
		}
		
		token = new QueryToken(tokenValue, tokenType);
		
		return token;
	}
	
	/**
	 * Private method that increments current index until next non-whitespace character.
	 * 
	 * @throws IllegalArgumentException
	 * 				if it finds new line in string
	 */
	private void skipSpaces() {
		while(index < data.length && Character.isWhitespace(data[index])) {
			if(data[index] == '\n') {
				throw new IllegalArgumentException("New lines are illegal!");
			}
			index++;
		}
	}
	
	/**
	 * Lexer state setter.
	 * 
	 * @param state
	 */
	public void setState(QueryLexerState state) {
		this.state = state;
	}
	
	/**
	 * Lexer state getter.
	 * 
	 * @return state
	 */
	public QueryLexerState getState() {
		return state;
	}

}
