package hr.fer.zemris.java.gui.calc;

/**
 * Interface unary operations with one function.
 * 
 * @author Petar Cvitanović
 *
 */
public interface IFunction {

	/**
	 * Method that executes calculation on given number.
	 * 
	 * @param a
	 * @return calculation result
	 */
	public double execute(double a);
	
}
