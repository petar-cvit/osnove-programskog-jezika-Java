package hr.fer.zemris.java.hw05.db;

import java.util.List;

/**
 * Filters records with given query.
 * 
 * @author Petar Cvitanović
 *
 */
public class QueryFilter implements IFilter{

	/**
	 * list of conditions in a query
	 */
	List<ConditionalExpressions> query;
	
	/**
	 * Constructor with list of conditions.
	 * 
	 * @param query
	 */
	public QueryFilter(List<ConditionalExpressions> query) {
		this.query = query;
	}
	
	@Override
	public boolean accepts(StudentRecord record) {
		boolean out = true;
		
		for(ConditionalExpressions expression : query) {
			out = out && expression.getComparisonOperator().satisfied(
					expression.getFieldGetter().get(record),
					expression.getStringLiteral());
		}
		
		return out;
	}

}
