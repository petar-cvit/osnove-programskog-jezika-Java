package hr.fer.zemris.java.custom.scripting.lexer;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;


class LexerTest {

	
	@Test
	public void constructor() {
		assertThrows(NullPointerException.class, () -> new Lexer(null));
	}
	
	@Test
	public void testPrazanString() {
		Lexer lexer = new Lexer("");
		
		assertEquals(TokenType.EOF, lexer.nextToken().getType(), "Empty input must generate only EOF token.");
	}

	@Test
	public void nakonEOF() {
		Lexer lexer = new Lexer("");

		lexer.nextToken();
		assertThrows(LexerException.class, () -> lexer.nextToken());
	}
	
	@Test
	public void lexerTest1() {
		Lexer lexer = new Lexer("slado\\\\led \\{$ {$= i i * @sin \"0.000\" @decfmt $}");
		
		TokenType[] expectedTypes = new TokenType[] {
				TokenType.STRING, 			
				TokenType.SYMBOL, 			
				TokenType.TAG,				
				TokenType.VARIABLE,			
				TokenType.VARIABLE,			
				TokenType.OPERATOR,
				TokenType.FUNCTION,
				TokenType.STRING,
				TokenType.FUNCTION,
				TokenType.SYMBOL
				};
		
		String[] expectedValues = new String[] {"slado\\led {$ ", "{", "=", "i", "i", "*", "sin", "0.000", "decfmt", "}"};
		
	Token token;			
		for (int i = 0; i < expectedTypes.length; i++) {
			token = lexer.nextToken();
			if(token.getType().equals(TokenType.SYMBOL) && token.getValue().equals("{")) {
				lexer.setState(LexerState.TAG);
			}
			if(token.getType().equals(TokenType.SYMBOL) && token.getValue().equals("}")) {
				lexer.setState(LexerState.BASIC);
			}
			assertEquals(expectedTypes[i], token.getType());
			assertEquals(expectedValues[i], token.getValue().toString());
		}
		
		token = lexer.nextToken();
		assertEquals(TokenType.EOF, token.getType());
	}
	
	@Test
	public void primjer9TokenTest() {
		Lexer lexer = new Lexer("Ovo se ruši {$ = \"\\n\" $}");
		
		TokenType[] expectedTypes = new TokenType[] {
				TokenType.STRING, 			
				TokenType.SYMBOL, 			
				TokenType.TAG,				
				TokenType.STRING,			
				TokenType.SYMBOL
				};
		
	Token token;			
		for (int i = 0; i < expectedTypes.length; i++) {
			token = lexer.nextToken();
			if(token.getType().equals(TokenType.SYMBOL) && token.getValue().equals("{")) {
				lexer.setState(LexerState.TAG);
			}
			if(token.getType().equals(TokenType.SYMBOL) && token.getValue().equals("}")) {
				lexer.setState(LexerState.BASIC);
			}
			assertEquals(expectedTypes[i], token.getType());
		}
		
		token = lexer.nextToken();
		assertEquals(TokenType.EOF, token.getType());
	}

	@Test
	public void lexerTest2() {
		Lexer lexer = new Lexer("tekst {$ FOR i 1 -1 -1.2 $} zelko {$= mama $}neki echo node \n {$END$}");
		
		TokenType[] types = {
				TokenType.STRING,
				TokenType.SYMBOL,
				TokenType.TAG,
				TokenType.VARIABLE,
				TokenType.INTEGER,
				TokenType.INTEGER,
				TokenType.DOUBLE,
				TokenType.SYMBOL,
				TokenType.STRING,
				TokenType.SYMBOL,
				TokenType.TAG,
				TokenType.VARIABLE,
				TokenType.SYMBOL,
				TokenType.STRING,
				TokenType.SYMBOL,
				TokenType.TAG,
				TokenType.SYMBOL
		};
		
		Token token;
		for (int i = 0; i < types.length; i++) {
			token = lexer.nextToken();
			if(token.getType().equals(TokenType.SYMBOL) && token.getValue().equals("{")) {
				lexer.setState(LexerState.TAG);
			}
			if(token.getType().equals(TokenType.SYMBOL) && token.getValue().equals("}")) {
				lexer.setState(LexerState.BASIC);
			}
			assertEquals(types[i], token.getType());
		
		}
		
		token = lexer.nextToken();
		assertEquals(TokenType.EOF, token.getType());
	}
	
	@Test
	public void zadacaPrimjer() {
		Lexer lexer = new Lexer("This is sample text.\n" + 
				"{$ FOR i 1 10 1 $}\n" + 
				" This is {$= i $}-th time this message is generated.\n" + 
				"{$END$}\n" + 
				"{$FOR i 0 10 2 $}\n" + 
				" sin({$=i$}^2) = {$= i i * @sin \"0.000\" @decfmt $}\n" + 
				"{$END$}");
		
		TokenType[] expectedTypes = new TokenType[] {
				TokenType.STRING, 			
				
				TokenType.SYMBOL, 			
				TokenType.TAG,				
				TokenType.VARIABLE,			
				TokenType.INTEGER,
				TokenType.INTEGER,
				TokenType.INTEGER,
				TokenType.SYMBOL,
				
				TokenType.STRING,
				
				TokenType.SYMBOL,
				TokenType.TAG,
				TokenType.VARIABLE,
				TokenType.SYMBOL,
				
				TokenType.STRING,
				
				TokenType.SYMBOL,
				TokenType.TAG,
				TokenType.SYMBOL,
				
				TokenType.STRING,
				
				TokenType.SYMBOL, 			
				TokenType.TAG,				
				TokenType.VARIABLE,			
				TokenType.INTEGER,
				TokenType.INTEGER,
				TokenType.INTEGER,
				TokenType.SYMBOL,
				
				TokenType.STRING,
				
				TokenType.SYMBOL,
				TokenType.TAG,
				TokenType.VARIABLE,
				TokenType.SYMBOL,
				
				TokenType.STRING,
				
				TokenType.SYMBOL, 			
				TokenType.TAG,				
				TokenType.VARIABLE,
				TokenType.VARIABLE,
				TokenType.OPERATOR,
				TokenType.FUNCTION,
				TokenType.STRING,
				TokenType.FUNCTION,
				TokenType.SYMBOL,
				
				TokenType.STRING,
				
				TokenType.SYMBOL,
				TokenType.TAG,
				TokenType.SYMBOL,
				
				};
		
	Token token;			
		for (int i = 0; i < expectedTypes.length; i++) {
			token = lexer.nextToken();
			if(token.getType().equals(TokenType.SYMBOL) && token.getValue().equals("{")) {
				lexer.setState(LexerState.TAG);
			}
			if(token.getType().equals(TokenType.SYMBOL) && token.getValue().equals("}")) {
				lexer.setState(LexerState.BASIC);
			}
			assertEquals(expectedTypes[i], token.getType());
			
		}
		
		token = lexer.nextToken();
		assertEquals(TokenType.EOF, token.getType());
	}
}
