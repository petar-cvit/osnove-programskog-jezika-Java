package hr.fer.zemris.java.hw05.db;

/**
 * This class offers variables of type IComparisonOperator interface.
 * Those variables represents operators.
 * 
 * @author Petar Cvitanović
 *
 */
public class ComparisonOperators {
	
	/**
	 * < operator
	 */
	public static final IComparisonOperator LESS = 
					(v1, v2) -> v1.compareTo(v2) < 0;
	
	/**
	 * <= operator
	 */
	public static final IComparisonOperator LESS_OR_EQUALS =
					(v1, v2) -> v1.compareTo(v2) <= 0;
			
	/**
	 * > operator
	 */
	public static final IComparisonOperator GREATER =
					(v1, v2) -> v1.compareTo(v2) > 0;
				
	/**
	 * >= operator
	 */
	public static final IComparisonOperator GREATER_OR_EQUALS =
					(v1, v2) -> v1.compareTo(v2) >= 0;
					
	/**
	 * = operator
	 */
	public static final IComparisonOperator EQUALS =
					(v1, v2) -> v1.compareTo(v2) == 0;
	
	/**
	 * != operator
	 */
	public static final IComparisonOperator NOT_EQUALS =
					(v1, v2) -> v1.compareTo(v2) != 0;
	
	/**
	 * LIKE operator. Every asterisk in string literal can be interpreted as
	 * any char sequence. Multiple asterisks are not allowed.
	 * 
	 */
	public static final IComparisonOperator LIKE =
					(v1, v2) -> {
						if(v2.contains("*")) {
							if(v1.length() + 1 < v2.length()) {
								return false;
							}
							
							boolean asterisk = false;
							int v1Index = 0;
							int v2Index = 0;
							
							while(v1Index < v1.length() && v2Index < v2.length()) {
								if(v2.charAt(v2Index) == '*' && asterisk) {
									throw new IllegalArgumentException("Multiple asterisks!");
								}
								
								if(v2.charAt(v2Index) == '*') {
									v1Index = v1.length() - (v2.length() - (++v2Index));
									asterisk = true;
									
								} else {
									if(v1.charAt(v1Index) != v2.charAt(v2Index)) {
										return false;
									}
									
									v1Index++;
									v2Index++;
								}
							}
							return true;
							
						} else {
							return v1.equals(v2);
						}
					};
					
}
