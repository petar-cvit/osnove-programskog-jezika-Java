package hr.fer.zemris.java.custom.scripting.exec;

/**
 * Represents binary function.
 * 
 * @author Petar Cvitanović
 *
 */
public interface IFunction {

	/**
	 * Performs binary function on given numbers and returns product.
	 * 
	 * @param a
	 * @param b
	 * @return product
	 */
	public Object execute(Object a, Object b);
}
