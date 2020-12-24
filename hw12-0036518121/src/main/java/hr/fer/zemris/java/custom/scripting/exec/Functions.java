package hr.fer.zemris.java.custom.scripting.exec;

/**
 * Class that holds functions as final static variables that are imlementations of {@link IFunction} interface.
 * 
 * @author Petar Cvitanović
 *
 */
public class Functions {

	/**
	 * Add function
	 */
	public static final IFunction ADD = (a, b) -> {
		if(a instanceof Double || b instanceof Double) {
			return Double.valueOf(((Number) a).doubleValue() + ((Number) b).doubleValue());
		}
		
		return Integer.valueOf(((Integer) a).intValue() + ((Integer) b).intValue());
	};
	
	/**
	 * Subtract function
	 */
	public static final IFunction SUB = (a, b) -> {
		if(a instanceof Double || b instanceof Double) {
			return Double.valueOf(((Number) a).doubleValue() - ((Number) b).doubleValue());
		}
		
		return Integer.valueOf(((Integer) a).intValue() - ((Integer) b).intValue());
	};
	
	/**
	 * Multiplication function
	 */
	public static final IFunction MUL = (a, b) -> {
		if(a instanceof Double || b instanceof Double) {
			return Double.valueOf(((Number) a).doubleValue() * ((Number) b).doubleValue());
		}
		
		return Integer.valueOf(((Integer) a).intValue() * ((Integer) b).intValue());
	};
	
	/**
	 * Divide function
	 */
	public static final IFunction DIV = (a, b) -> {
		if(a instanceof Double || b instanceof Double) {
			return Double.valueOf(((Number) a).doubleValue() / ((Number) b).doubleValue());
		}
		
		return Integer.valueOf(((Integer) a).intValue() / ((Integer) b).intValue());
	};
	
	/**
	 * Comparing two {@link Number} instances. If first is greater than the other returns 1, if the other is bigger
	 * returns -1. If numbers are equal returns 0.
	 */
	public static final IFunction COMPARE = (a, b) -> {
		return Double.compare(((Number) a).doubleValue(), ((Number) b).doubleValue());
	};
}
