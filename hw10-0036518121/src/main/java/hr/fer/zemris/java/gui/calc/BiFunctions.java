package hr.fer.zemris.java.gui.calc;

import java.util.function.DoubleBinaryOperator;

/**
 * Class with binary functions as static final variables.
 * 
 * @author Petar Cvitanović
 *
 */
public class BiFunctions {
	
	/**
	 * adding
	 */
	public static final DoubleBinaryOperator ADD = (a, b) -> a + b;
	
	/**
	 * subtracting
	 */
	public static final DoubleBinaryOperator SUB = (a, b) -> a - b;
  	
	/**
	 * multiplying
	 */
	public static final DoubleBinaryOperator MUL = (a, b) -> a * b;
	
	/**
	 * dividing
	 */
	public static final DoubleBinaryOperator DIV = (a, b) -> a / b;
	
	/**
	 * potentiation
	 */
	public static final DoubleBinaryOperator POW = (a, b) -> Math.pow(a, b);
	
	/**
	 * rooting
	 */
	public static final DoubleBinaryOperator ROOT = (a, b) -> Math.pow(a, 1. / b);

}
