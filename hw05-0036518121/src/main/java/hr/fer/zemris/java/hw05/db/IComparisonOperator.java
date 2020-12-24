package hr.fer.zemris.java.hw05.db;

/**
 * Compares two strings with it's satisfied method.
 * 
 * @author Petar Cvitanović
 *
 */
public interface IComparisonOperator {
	
	/**
	 * Returns true if some condition is fulfilled.
	 * 
	 * @param value1
	 * @param value2
	 * @return boolean
	 */
	public boolean satisfied(String value1, String value2);

}
