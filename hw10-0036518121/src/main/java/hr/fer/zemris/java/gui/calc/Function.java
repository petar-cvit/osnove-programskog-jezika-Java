package hr.fer.zemris.java.gui.calc;

/**
 * Class with unary functions as static final variables.
 * 
 * @author Petar Cvitanović
 *
 */
public class Function {
	
	/**
	 * sine
	 */
	public static final IFunction SIN = a -> Math.sin(a);

	/**
	 * arc sine
	 */
	public static final IFunction ARC_SIN = a -> Math.asin(a);

	/**
	 * cosine
	 */
	public static final IFunction COS = a -> Math.cos(a);

	/**
	 * arc cosine
	 */
	public static final IFunction ARC_COS = a -> Math.acos(a);

	/**
	 * tangent
	 */
	public static final IFunction TAN = a -> Math.tan(a);

	/**
	 * arc tangent
	 */
	public static final IFunction ARC_TAN = a -> Math.atan(a);

	/**
	 * cotangent
	 */
	public static final IFunction CTG = a -> Math.cos(a) / Math.sin(a);

	/**
	 * arc cotangent
	 */
	public static final IFunction ARC_CTG = a -> Math.sin(a) / Math.cos(a);

	/**
	 * base 10 logarithm
	 */
	public static final IFunction LOG = a -> Math.log10(a);

	/**
	 * 10 to the power of given number
	 */
	public static final IFunction POWER_10 = a -> Math.pow(10, a);

	/**
	 * natural logarithm
	 */
	public static final IFunction LN = a -> Math.log(a);

	/**
	 * raised to the power of e
	 */
	public static final IFunction POWER_E = a -> Math.pow(a, Math.E);
	
	/**
	 * reciprocal
	 */
	public static final IFunction INVERT = a -> 1. / a;

	
}
