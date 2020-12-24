package hr.fer.zemris.java.hw06.shell.parser;

import java.util.ArrayList;
import java.util.List;

/**
 * Parses string to list of arguments. If text is inside quotation marks it is
 * interpreted as one argument.
 * 
 * @author Petar Cvitanović
 *
 */
public class Parser {
	
	/**
	 * text that needs to be parsed
	 */
	private String text;
	
	/**
	 * list of parsed arguments
	 */
	private List<String> arguments;
	
	/**
	 * Constructor with text that needs to be parsed.
	 * 
	 * @param text
	 */
	public Parser(String text) {
		this.text = text;
		arguments = new ArrayList<String>();
		
		try {
			parse();
		} catch (Exception e) {
			throw new IllegalArgumentException("Unable to parse!");
		}
	}
	
	/**
	 * Parse method uses lexer to get tokens. From those tokens parse method forms
	 * list of arguments.
	 */
	private void parse() {
		Token token;
		Lexer lexer = new Lexer(text);
		
		while(true) {
			token = lexer.nextToken();
			
			if(token.getType() == TokenType.SYMBOL) {
				lexer.setState(lexer.getState() == LexerState.BASIC ? LexerState.QUOTATION : LexerState.BASIC);
			} else if(token.getType() == TokenType.EOF) {
				break;
			} else { 
				arguments.add(token.getValue());
			}
		}
	}
	
	/**
	 * Returns parsed arguments.
	 * 
	 * @return arguments
	 */
	public List<String> getArguments() {
		return arguments;
	}

}
