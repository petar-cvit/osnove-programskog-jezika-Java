package hr.fer.zemris.java.hw05.db;

import java.util.ArrayList;
import java.util.List;

/**
 * Parser class parses queries in string form to list of conditional expressions.
 * 
 * @author Petar Cvitanović
 *
 */
public class QueryParser {
	
	/**
	 * data to be parsed
	 */
	private String data;
	
	/**
	 * list of conditions
	 */
	private List<ConditionalExpressions> query;
	
	/**
	 * Constructor with string to be parsed.
	 * 
	 * @param data
	 */
	public QueryParser(String data) {
		this.data = data;
		this.query = new ArrayList<ConditionalExpressions>();
		
		try {
			parse();
		} catch(Exception ex) {
			throw new IllegalArgumentException(ex.getMessage());
		}
	}
	
	/**
	 * Returns whether or not parsed string is parsed to direct query. Direct query
	 * has one condition with equals operator and JMBAG field.
	 * 
	 * @return boolean
	 */
	public boolean isDirectQuery() {
		return query.size() == 1 && query.get(0).getFieldGetter().equals(FieldValueGetters.JMBAG)
				&& query.get(0).getComparisonOperator().equals(ComparisonOperators.EQUALS);
	}
	
	/**
	 * Returns JMBAG that was given in equality comparison.
	 * 
	 * @throws IllegalArgumentException
	 * 				if query is not direct.
	 * @return JMBAG
	 */
	public String getQueriedJMBAG() {
		if(!isDirectQuery()) {
			throw new IllegalArgumentException("Query is not direct!");
		} else {
			return query.get(0).getStringLiteral();
		}
	}
	
	/**
	 * Returns list of condition.
	 * 
	 * @return parsed query
	 */
	public List<ConditionalExpressions> getQuery(){
		return query;
	}
	
	/**
	 * Parses queries using lexer. Lexer returns tokens which are converted to conditions.
	 * 
	 * @throws IllegalArgumentException
	 */
	private void parse() {
		QueryLexer lexer = new QueryLexer(data);
		QueryToken token;
		
		IFieldValueGetter getter;
		String literal;
		IComparisonOperator operator;
		
		while(true) {
			
			token = lexer.nextToken();
			
			if(token.getType() != QueryTokenType.ATRIBUTE) {
				throw new IllegalArgumentException("Invalid sequence!");
			}
			
			String atribute = token.getValue();
			
			switch(atribute) {
			case "jmbag":
				getter = FieldValueGetters.JMBAG;
				break;
				
			case "firstName":
				getter = FieldValueGetters.FIRST_NAME;
				break;
				
			case "lastName":
				getter = FieldValueGetters.LAST_NAME;
				break;
				
			default:
				throw new IllegalArgumentException("Invalid atribute name: " + atribute);
			}
			
			token = lexer.nextToken();
			
			if(token.getType() != QueryTokenType.OPERATOR) {
				throw new IllegalArgumentException("Invalid sequnce!");
			}
			
			String operatorValue = token.getValue();
			
			switch(operatorValue) {
			case "<":
				operator = ComparisonOperators.LESS;
				break;
				
			case "<=":
				operator = ComparisonOperators.LESS_OR_EQUALS;
				break;
				
			case ">":
				operator = ComparisonOperators.GREATER;
				break;
				
			case ">=":
				operator = ComparisonOperators.GREATER_OR_EQUALS;
				break;
				
			case "=":
				operator = ComparisonOperators.EQUALS;
				break;
				
			case "!=":
				operator = ComparisonOperators.NOT_EQUALS;
				break;
				
			case "LIKE":
				operator = ComparisonOperators.LIKE;
				break;
				
			default:
				throw new IllegalArgumentException("Invalid operator!");
			}
			
			token = lexer.nextToken();
			
			if(token.getType() != QueryTokenType.SYMBOL) {
				throw new IllegalArgumentException("Missing parentheses!");
			}
			
			lexer.setState(QueryLexerState.LITERAL);
			
			token = lexer.nextToken();
			literal = token.getValue();
			
			if(lexer.nextToken().getType() != QueryTokenType.SYMBOL) {
				throw new IllegalArgumentException("Missing parenthesis!");
			}
			
			lexer.setState(QueryLexerState.BASIC);
			
			query.add(new ConditionalExpressions(getter, literal, operator));
			token = lexer.nextToken();
			
			if(token.getType() == QueryTokenType.EOF) {
				break;
			} else if(token.getType() != QueryTokenType.AND) {
				throw new IllegalArgumentException("Illegal token!(AND or EOF tokens expected)");
			}
		}
	}

}


















