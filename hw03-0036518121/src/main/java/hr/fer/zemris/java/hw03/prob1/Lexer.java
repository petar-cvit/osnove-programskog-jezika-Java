package hr.fer.zemris.java.hw03.prob1;

/**
 * Lexer class converts string to tokens. Lexer can be in two states.
 * 
 * @author Petar Cvitanović
 */
public class Lexer {
	
	/**
	 * text that needs to be tokenized in a form of character array
	 */
	private char[] data;
	
	/**
	 * last generated token
	 */
	private Token token;
	
	/**
	 * index of current character in text
	 */
	private int currentIndex;
	
	/**
	 * state of the lexer
	 */
	private LexerState lexerState;
	
	/**
	 * Constructor with text to tokenize
	 * 
	 * @throws NullPointerException
	 * 				if text is null
	 * 
	 * @param text
	 */
	public Lexer(String text) {
		if(text == null) {
			throw new NullPointerException("Text cannot be null!");
		}
		
		data = text.trim().toCharArray();
		token = null;
		currentIndex = 0;
		lexerState = LexerState.BASIC;
	}
	
	/**
	 * Setter for state of the lexer.
	 * 
	 * @throws NullPointerException
	 * 				if given state is null
	 * 
	 * @param state
	 */
	public void setState(LexerState state) {
		if(state == null) {
			throw new NullPointerException("Lexer state cannot be null!");
		}
		
		lexerState = state;
	}
	
	/**
	 * Returns next token. Calls methods to generate new tokens based on lexer's state.
	 * 
	 * @return next token
	 */
	public Token nextToken() {
		removeSpaces();
		
		if(token != null && token.getType().equals(TokenType.EOF)) {
			throw new LexerException("No more tokens!");
			
		} else if(currentIndex == data.length) {
			 token = new Token(TokenType.EOF, null);
			 return token;
		}
		
		if(lexerState == LexerState.BASIC) {
			return basicToken();
		} else {
			return extendedToken();
		}
	}

	/**
	 * Returns new token when lexer is outside of text delimited by '#' characters.
	 * 
	 * @throws LexerException
	 * 
	 * @return next token
	 */
	public Token basicToken() {
		
		TokenType nextType = getType(data[currentIndex]);
		
		if(escape()) {
			currentIndex++;
			nextType = TokenType.WORD;
		}
		
		if(nextType == TokenType.SYMBOL) {
			token = new Token(nextType, data[currentIndex]);
			currentIndex++;
			
		} else {
			String tokenValue = Character.toString(data[currentIndex++]);
			
			for(int i = currentIndex;currentIndex < data.length;i++) {
				if(escape()) {
					
					if(nextType == TokenType.WORD) {
						currentIndex++;
					} else {
						break;
					}
					
				} else if (getType(data[currentIndex]) != nextType) {
					break;
				}
				
				tokenValue = tokenValue.concat(Character.toString(data[currentIndex++]));
			}
			
			if(nextType == TokenType.NUMBER) {
				try{
					token = new Token(nextType, Long.parseLong(tokenValue));
				} catch (NumberFormatException ex) {
					throw new LexerException("Cannot parse "+tokenValue+" to number!");
				}
			} else {
				token = new Token(nextType, tokenValue);
			}
		}
		
		return token;
	}
	
	/**
	 * Returns new token when lexer is inside of text delimited by '#' characters.
	 * 
	 * @return next token
	 */
	public Token extendedToken() {
		String tokenValue = Character.toString(data[currentIndex++]);
		
		if(tokenValue.equals("#")) {
			token = new Token(TokenType.SYMBOL, '#');
			return token;
		}
		
		for(int i = currentIndex;currentIndex < data.length;i++) {
			if(Character.isWhitespace(data[currentIndex]) || data[currentIndex] == '#') {
				break;
			}
			tokenValue = tokenValue.concat(Character.toString(data[currentIndex++]));
		}
		
		token = new Token(TokenType.WORD, tokenValue);
		return token;
	}
	
	/**
	 * Returns last generated token. Doesn't generate new tokens.
	 * 
	 * @return last generated token
	 */
	public Token getToken() {
		return token;
	}

	/**
	 * Increments current index until next non space.
	 */
	private void removeSpaces() {
		while(currentIndex < data.length) {
			if(Character.isWhitespace(data[currentIndex])) {
				currentIndex++;
			} else {
				break;
			}
		}
	}
	
	/**
	 * 
	 * Returns value depending on whether it happened
	 * 
	 * @throws LexerException
	 * 				if escape is invalid
	 * 
	 * @return boolean
	 */
	private boolean escape() {
		if(data[currentIndex] == '\\') { 
			if(currentIndex + 1 == data.length || (!Character.isDigit(data[currentIndex + 1]) 
					&& data[currentIndex + 1] != '\\')) {
				throw new LexerException("Inavlid escape!");
				
			} else {
				return true;
			}
		}
		
		return false;
	}
	
	/**
	 * Returns type of token depending on charcter's value.
	 * 
	 * @param c
	 * @return type of token
	 */
	private static TokenType getType(char c) {
		if(Character.isAlphabetic(c)) {
			return TokenType.WORD;
		} else if(Character.isDigit(c)) {
			return TokenType.NUMBER;
		} else {
			return TokenType.SYMBOL;
		}
	}
}








