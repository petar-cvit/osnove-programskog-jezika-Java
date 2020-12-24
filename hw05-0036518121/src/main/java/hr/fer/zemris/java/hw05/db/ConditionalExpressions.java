package hr.fer.zemris.java.hw05.db;

/**
 * This class represents one condition of the query. It is defined with field getter, operator
 * and string literal.
 * 
 * @author Petar Cvitanović
 *
 */
public class ConditionalExpressions {
	
	/**
	 * condition field
	 */
	private IFieldValueGetter getter;
	
	/**
	 * string literal used for comparing
	 */
	private String stringLiteral;
	
	/**
	 * operator
	 */
	private IComparisonOperator operator;
	
	/**
	 * Constructor with field getter, string literal and operator.
	 * 
	 * @param getter
	 * @param stringLiteral
	 * @param operator
	 */
	public ConditionalExpressions(IFieldValueGetter getter, String stringLiteral,
			IComparisonOperator operator) {
		this.getter = getter;
		this.stringLiteral = stringLiteral;
		this.operator = operator;
	}

	/**
	 * Field getter getter.
	 * 
	 * @return field getter
	 */
	public IFieldValueGetter getFieldGetter() {
		return getter;
	}

	/**
	 * String literal getter.
	 * 
	 * @return string literal
	 */
	public String getStringLiteral() {
		return stringLiteral;
	}

	/**
	 * Operator getter.
	 * 
	 * @return operator
	 */
	public IComparisonOperator getComparisonOperator() {
		return operator;
	}
}
