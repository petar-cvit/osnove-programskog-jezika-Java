package hr.fer.zemris.java.hw06.shell.parser;

/**
 * Lexer class returns given text in the form of tokens. Lexer has two states: inside parenthesis
 * and outside parenthesis. Tokens in parenthesis can contain whitespaces, but tokens outside parathesis
 * can't. Tokens inside parenthesis escape '\' and '"'.
 * 
 * @author Petar Cvitanović
 *
 */
public class Lexer {

	/**
	 * index of current character
	 */
	private int currentIndex;

	/**
	 * data to be processed ad character array
	 */
	private char[] data;

	/**
	 * last token that was returned
	 */
	private Token token;

	/**
	 * lexer's state, initially BASIC
	 */
	private LexerState state;

	/**
	 * Constructor with string to be processed. This string is converted to character array.
	 * Sets lexer's state to basic and index to zero. 
	 * 
	 * @param text
	 */
	public Lexer(String text) {
		currentIndex = 0;
		data = text.toCharArray();
		token = null;
		state = LexerState.BASIC;
	}

	/**
	 * Returns next token based on lexer's state.
	 * 
	 * @return next token
	 */
	public Token nextToken() {
		if(data.length == currentIndex) {
			currentIndex++;
			token = new Token("End of file", TokenType.EOF);
			return token;
		}

		if(token != null && token.getValue().equals(TokenType.EOF)) {
			throw new IllegalStateException("End of file");
		}

		StringBuilder sb = new StringBuilder();

		if(state.equals(LexerState.BASIC)) {
			if(Character.isWhitespace(data[currentIndex])) {
				currentIndex++;
			}
			
			if(data[currentIndex] == '\"') {
				currentIndex++;
				token = new Token("quotations", TokenType.SYMBOL);
			} else {
				while(currentIndex < data.length && !Character.isWhitespace(data[currentIndex])) {
					sb.append(data[currentIndex++]);
				}

				token = new Token(sb.toString(), TokenType.TEXT);
			}
		} else {
			if(data[currentIndex] == '\"') {
				currentIndex++;
				token = new Token("quotations", TokenType.SYMBOL);
			} else {
				
				if(currentIndex == data.length) {
					throw new NullPointerException();
				}

				while(data[currentIndex] != '\"') {
					if(data[currentIndex] == '\\' && 
							(data[currentIndex + 1] == '\\' || data[currentIndex + 1] == '\"')) {
						sb.append(data[++currentIndex]);
						currentIndex++;
					} else { 
						sb.append(data[currentIndex++]);
					}
				}

				token = new Token(sb.toString(), TokenType.TEXT);
			}
		}

		return token;
	}

	/**
	 * Lexer's state setter;
	 * 
	 * @param state
	 */
	public void setState(LexerState state) {
		this.state = state;
	}

	/**
	 * Lexer's state getter.
	 * 
	 * @return current state
	 */
	public LexerState getState() {
		return state;
	}
}
