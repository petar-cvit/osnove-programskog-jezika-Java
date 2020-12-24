package hr.fer.zemris.java.custom.scripting.lexer;

/**
 * Lexer class converts string to tokens. Tokens are later processed in parser class.
 * 
 * @author Petar Cvitanović
 *
 */
public class Lexer {

	/**
	 * string in a data array
	 */
	private char[] data;
	
	/**
	 * last token generated
	 */
	private Token token;
	
	/**
	 * current index in data array
	 */
	private int currentIndex;
	
	/**
	 * can be outside or inside of a tag
	 */
	private LexerState lexerState;
	
	/**
	 * flag used to determine whether or not a tag has it's name
	 */
	private boolean name = false;
	
	/**
	 * If lexer is outside of tags it is not used. If lexer is in tag it changes values.
	 * If it's echo tag elements is set to -1. If it's for tag it counts the number of elements.
	 */
	private int elements = 0;
	
	/**
	 * Constructor with text to tokenize.
	 */
	public Lexer(String text) {
		data = text.trim().toCharArray();
		currentIndex = 0;
		token = null;
		lexerState = LexerState.BASIC;
	}
	
	/**
	 * Used to set lexer's state.
	 * 
	 * @throws NullPointerException
	 * 				if given state is null
	 */
	public void setState(LexerState state) {
		if(state == null) {
			throw new NullPointerException("Lexer state cannot be null!");
		}
		
		lexerState = state;
	}
	
	/**
	 * State getter
	 * 
	 * @return state
	 */
	public LexerState getState() {
		return lexerState;
	}
	
	/**
	 * Returns next token.
	 * 
	 * @throws Lexer Exception
	 */
	public Token nextToken() {
		
		if(token != null && token.getType().equals(TokenType.EOF)) {
			throw new LexerException("No more tokens!");
			
		} else if(currentIndex == data.length) {
			 token = new Token(TokenType.EOF, "End od file");
			 return token;
		}

		String tokenValue = null;
		
		if(lexerState == LexerState.BASIC) {
			if(escapeBasic()) {
				currentIndex++;
				tokenValue = Character.toString(data[currentIndex++]);
			
			} else if(data[currentIndex] == '{' && data[currentIndex + 1] == '$') {
				//lexerState = lexerState.TAB;
				token = new Token(TokenType.SYMBOL, Character.toString('{'));
				currentIndex += 2;
				return token;
				
			} else {
				tokenValue = Character.toString(data[currentIndex++]);
			}
			
			while(currentIndex < data.length) {
				if(escapeBasic()) {
					currentIndex++;
				} else if(data[currentIndex] == '{' && data[currentIndex + 1] == '$') {
					break;
				}
				
				tokenValue = tokenValue.concat(Character.toString(data[currentIndex++]));
			}
			token = new Token(TokenType.STRING, tokenValue);
			
		} else {
			
			removeSpaces();
	
			if(data[currentIndex] == '$' && data[currentIndex+1] == '}') {
				currentIndex += 2;
				name = false;
				//lexerState = lexerState.BASIC;
				
				if(elements < 3 && elements > -1) {
					throw new LexerException("For loop has less than 3 elements!");
				}
				
				token = new Token(TokenType.SYMBOL, Character.toString('}'));
				return token;
			}
			
			if(!name) {
				name = true;
				String tagName = variableName();
				if(tagName == null && data[currentIndex] != '=') {
					throw new LexerException("Tag name is unacceptable!");
				} else {
					if(data[currentIndex] == '=') {
						currentIndex++;
						tagName = Character.toString('=');
					}
					if(tagName.toLowerCase().equals("for")) {
						elements = 0;
					} else {
						elements = -1;
					}
					
					token = new Token(TokenType.TAG, tagName);
				}
			} else {
				if(elements > 3) {
					throw new LexerException("For loop has too many elements!");
				}
				
				if(Character.isAlphabetic(data[currentIndex]) && elements == -1 ||
						elements == 0) {
					tokenValue = variableName();
					token = new Token(TokenType.VARIABLE, tokenValue);
					if(elements > -1) {
						elements++;
					}
					return token;
				}
					
				if(data[currentIndex] == '\"' && tokenValue == null) {
					
					currentIndex++;
					
					if(data[currentIndex] == '\\' && data[currentIndex + 1] == '\\') {
						tokenValue = Character.toString('\\');
						currentIndex += 2;
						
					} else if(data[currentIndex] == '\\' && data[currentIndex + 1] == '\"') {
						tokenValue = Character.toString('\"');
						currentIndex += 2;
						
					} else if(data[currentIndex] == '\\' && data[currentIndex + 1] == 't') {
						tokenValue = Character.toString('\t');
						currentIndex += 2;
						
					} else if(data[currentIndex] == '\\' && data[currentIndex + 1] == 'r') {
						tokenValue = Character.toString('\r');
						currentIndex += 2;
						
					} else if(data[currentIndex] == '\\' && data[currentIndex + 1] == 'n') {
						tokenValue = Character.toString('\n');
						currentIndex += 2;
						
					} else {
						tokenValue = Character.toString(data[currentIndex++]);
					}
					
					while(data[currentIndex] != '\"') {		
						if(data[currentIndex] == '\\') {
							if(data[currentIndex + 1] == '\"' || data[currentIndex + 1] == '\\') {
								currentIndex++;
								
							} else if(data[currentIndex + 1] == 't') {
								tokenValue = tokenValue.concat(Character.toString('\t'));
								currentIndex+=2;
								continue;
								
							} else if(data[currentIndex + 1] == 'n') {
								tokenValue = tokenValue.concat(Character.toString('\n'));
								currentIndex+=2;
								continue;
								
							} else if(data[currentIndex + 1] == 'r') {
								tokenValue = tokenValue.concat(Character.toString('\r'));
								currentIndex+=2;
								continue;
								
							} else {
								throw new LexerException("Invalid escape!");
							}
						}
						
						tokenValue = tokenValue.concat(Character.toString(data[currentIndex++]));
						
						if(currentIndex == data.length) {
							throw new LexerException("String has no end!");
						}
					}
					currentIndex++;
					token = new Token(TokenType.STRING, tokenValue);
				}
				
				if(data[currentIndex] == '@' && tokenValue == null) {
					currentIndex++;
					tokenValue = variableName();
					token = new Token(TokenType.FUNCTION, tokenValue);
				}
				
				if((Character.isDigit(data[currentIndex]) || data[currentIndex] == '-' && Character.isDigit(data[currentIndex+1])) && tokenValue == null) {
					tokenValue = Character.toString(data[currentIndex++]);
					
					while(Character.isDigit(data[currentIndex]) || data[currentIndex] == '.') {
						tokenValue = tokenValue.concat(Character.toString(data[currentIndex++]));
					}

					try {
						if(tokenValue.indexOf('.') == -1) {
							token = new Token(TokenType.INTEGER, Integer.parseInt(tokenValue));
						} else {
							token = new Token(TokenType.DOUBLE, Double.parseDouble(tokenValue));
						}
					} catch (NumberFormatException ex) {
						throw new LexerException("Unable to parse to number: "+tokenValue);
					}
				}
				
				if("+-*/^".indexOf(data[currentIndex]) != -1  && tokenValue == null) {
					tokenValue = Character.toString(data[currentIndex++]);
					token = new Token(TokenType.OPERATOR, tokenValue);
				}
				
				
			}
		}
		
		if(elements > 0) {
			elements++;
		}
		
		return token;
	}
	
	/**
	 * Returns last generated token.
	 * 
	 * @return token
	 */
	public Token getToken() {
		return token;
	}
	
	/**
	 * Used to determine whether or not the escape is legal.
	 * 
	 * @throws LexerException
	 * 				if escape is invalid
	 * 
	 * @return boolean
	 */
	private boolean escapeBasic() {
		if(data[currentIndex] == '\\') { 
			if(currentIndex + 1 == data.length || (data[currentIndex + 1] != '{' 
					&& data[currentIndex + 1] != '\\')) {
				throw new LexerException("Inavlid escape!");
				
			} else {
				return true;
			}
		}
		
		return false;
	}

	/**
	 * Checks if characters until the next space form a valid variable name.
	 * 
	 * @throws LexerException
	 * 				if variable name is invalid
	 * 
	 * @return null if name is not valid or a string representing variable name.
	 */
	private String variableName() {
		String var = null;
		if(Character.isAlphabetic(data[currentIndex])) {
			var = Character.toString(data[currentIndex++]);
		} else {
			return null;
		}
		while(currentIndex < data.length) {
			if(Character.isWhitespace(data[currentIndex]) || data[currentIndex] == '$') {
				break;
			}
			if(!(Character.isAlphabetic(data[currentIndex])
					|| Character.isDigit(data[currentIndex])
					|| data[currentIndex] == '_')) {
				throw new LexerException("Invalid variable name:" +var);
			}
		
			var = var.concat(Character.toString(data[currentIndex++]));
		}
		
		return var;
	}
	
	/**
	 * Increments current index until next non space.
	 */
	private void removeSpaces() {
		while(currentIndex < data.length) {
			if(data[currentIndex] != ' ') {
				break;
			}
			currentIndex++;
		}
	}

}
