package hr.fer.zemris.java.custom.scripting.parser;

import hr.fer.zemris.java.custom.collections.ArrayIndexedCollection;
import hr.fer.zemris.java.custom.collections.EmptyStackException;
import hr.fer.zemris.java.custom.collections.ObjectStack;
import hr.fer.zemris.java.custom.scripting.elems.Element;
import hr.fer.zemris.java.custom.scripting.elems.ElementConstantDouble;
import hr.fer.zemris.java.custom.scripting.elems.ElementConstantInteger;
import hr.fer.zemris.java.custom.scripting.elems.ElementFunction;
import hr.fer.zemris.java.custom.scripting.elems.ElementOperator;
import hr.fer.zemris.java.custom.scripting.elems.ElementString;
import hr.fer.zemris.java.custom.scripting.elems.ElementVariable;
import hr.fer.zemris.java.custom.scripting.lexer.Lexer;
import hr.fer.zemris.java.custom.scripting.lexer.LexerException;
import hr.fer.zemris.java.custom.scripting.lexer.LexerState;
import hr.fer.zemris.java.custom.scripting.lexer.Token;
import hr.fer.zemris.java.custom.scripting.lexer.TokenType;
import hr.fer.zemris.java.custom.scripting.nodes.DocumentNode;
import hr.fer.zemris.java.custom.scripting.nodes.EchoNode;
import hr.fer.zemris.java.custom.scripting.nodes.ForLoopNode;
import hr.fer.zemris.java.custom.scripting.nodes.Node;
import hr.fer.zemris.java.custom.scripting.nodes.TextNode;

/**
 * Parser class creates tree from nodes. Root node is document node.
 * Only document node and for loop node can have children.
 * 
 * @author Petar Cvitanović
 *
 */
public class SmartScriptParser {

	/**
	 * root node
	 */
	DocumentNode documentNode;
	
	/**
	 * parser's lexer
	 */
	Lexer lexer;
	
	/**
	 * stack used to form a tree
	 */
	ObjectStack stack;
	
	/**
	 * last element generated from lexer's tokens
	 */
	Element element;
	
	/**
	 * Constructor with text. Calls parse function.
	 * 
	 * @throws SmartScriptParserException
	 * 
	 * @param text
	 */
	public SmartScriptParser(String text) {
		if(text == null) {
			throw new SmartScriptParserException("Text cannot be null!");
		}
		
		lexer = new Lexer(text);
		stack = new ObjectStack();
		
		documentNode = new DocumentNode();
		stack.push(documentNode);
		
		try {
			parse();
		} catch (LexerException | NullPointerException ex) {
			throw new SmartScriptParserException(ex.getMessage());
		}/* catch (Exception exception) {
			throw new SmartScriptParserException();
		}*/

	}
	
	/**
	 * Creates elements from lexer's tokens. From those elements creates nodes that form a tree.
	 * 
	 * @throws SmartScriptParserException 
	 * 
	 * @throws LexerException
	 */
	public void parse() {

		Token token = lexer.nextToken();
		
		while(!token.getType().equals(TokenType.EOF)) {
			
			if(token.getType().equals(TokenType.SYMBOL)) {
				
				lexer.setState(LexerState.TAG);
				
				token = lexer.nextToken();
				String tagName = (String) token.getValue();
				
				if(tagName.toLowerCase().equals("for")) {
					
					ElementVariable variable = (ElementVariable) getNextElement(lexer.nextToken());
					Element start = getNextElement(lexer.nextToken());
					Element end = getNextElement(lexer.nextToken());
					
					token = lexer.nextToken();
					Element step = null;
					
					if(!token.getType().equals(TokenType.SYMBOL)) {
						step = getNextElement(token);
					}
					while(!token.getType().equals(TokenType.SYMBOL)) {
						token = lexer.nextToken();
					}
					
					ForLoopNode forNode = new ForLoopNode(variable, start, end, step);
					
					Node n;
					
					try {
						n = (Node) stack.peek();
					} catch(EmptyStackException ex) {
						throw new SmartScriptParserException("End tag before loop tag!");
					}
					
					n.addChildNode(forNode);
					stack.push(forNode);
					
				} else if(tagName.toLowerCase().equals("=")){
					
					ArrayIndexedCollection elements = new ArrayIndexedCollection();
					
					while(true) {
						token = lexer.nextToken();
						if(token.getType().equals(TokenType.SYMBOL)) {
							break;
						}
						
						elements.add(getNextElement(token));
						
					}
					
					Element[] a = new Element[elements.toArray().length];
					int i = 0;
					for (Object o : elements.toArray()) {
					   a[i++] = (Element) o;
					}
					
					EchoNode echoNode = new EchoNode(a);
					
					Node n;
					
					try{
						n = (Node) stack.peek();
					} catch (EmptyStackException ex) {
						throw new SmartScriptParserException("End tag before loop tag!");
					}
					
					n.addChildNode(echoNode);
					
				} else if(tagName.toLowerCase().equals("end")){
					stack.pop();
					if(stack.isEmpty()) {
						throw new SmartScriptParserException("End tag before loop tag!");
					}
					lexer.nextToken();
				} else {
					throw new SmartScriptParserException("Invalid tag name!");
				}
				
				lexer.setState(LexerState.BASIC);
				
			} else {
				
				Node n;
				
				try {
					n = (Node) stack.peek();
				} catch(EmptyStackException ex) {
					throw new SmartScriptParserException("End tag before loop tag!");
				}
			
				n.addChildNode(new TextNode((String) token.getValue()));
			}
		token = lexer.nextToken();
		}
		if(!(stack.peek() == documentNode)) {
			throw new SmartScriptParserException("Unbalanced tree!");
		}
	}
	
	/**
	 * Returns root node
	 * 
	 * @return root node
	 */
	public DocumentNode getDocumentNode() {
		return documentNode;
	}
	
	/**
	 * Converts tokens to elements
	 * 
	 * @throws SmartScriptParserException
	 * 				if token is invalid type
	 * 
	 * @param token
	 * @return element based on token type
	 */
	public Element getNextElement(Token token) {

		switch (token.getType()) {
		case STRING:
			return new ElementString((String) token.getValue());
		case INTEGER:
			return new ElementConstantInteger((int) token.getValue());
		case DOUBLE:
			return new ElementConstantDouble((double) token.getValue());
		case VARIABLE:
			return new ElementVariable((String) token.getValue());
		case FUNCTION:
			return new ElementFunction((String) token.getValue());
		case OPERATOR:
			return new ElementOperator((String) token.getValue());
		default:
			throw new SmartScriptParserException("Invalid type of token: "+token.getType());
		}

	}	
}




















