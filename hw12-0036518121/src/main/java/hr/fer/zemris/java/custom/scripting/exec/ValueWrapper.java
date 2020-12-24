package hr.fer.zemris.java.custom.scripting.exec;

/**
 * Class that wraps object and allows operations on that object if that object is instance of some class that
 * extends {@link Number} class.
 * 
 * @author Petar Cvitanović
 *
 */
public class ValueWrapper {

	/**
	 * wrapped value
	 */
	private Object value;

	/**
	 * Constructor with value.
	 * 
	 * @param value
	 */
	public ValueWrapper(Object value) {
		super();
		this.value = value;
	}
	
	/**
	 * Value getter.
	 * 
	 * @return value
	 */
	public Object getValue() {
		return value;
	}

	/**
	 * Value setter.
	 * 
	 * @param value
	 */
	public void setValue(Object value) {
		this.value = value;
	}

	/**
	 * Add operation.
	 * 
	 * @param incValue
	 */
	public void add(Object incValue) {
		value = calculate(Functions.ADD, value, incValue);
	}

	/**
	 * Subtract operation.
	 * 
	 * @param decValue
	 */
	public void subtract(Object decValue) {
		value = calculate(Functions.SUB, value, decValue);
	}

	/**
	 * Multiply operation.
	 * 
	 * @param mulValue
	 */
	public void multiply(Object mulValue) {
		value = calculate(Functions.MUL, value, mulValue);
	}

	/**
	 * Divide operation.
	 * 
	 * @param divValue
	 */
	public void divide(Object divValue) {
		value = calculate(Functions.DIV, value, divValue);
	}

	/**
	 * Comparing operation.
	 * 
	 * @param withValue
	 * @return 1 if this is greater than given object, 0 if equals, -1 if less.
	 */
	public int numCompare(Object withValue) {
		return (int)calculate(Functions.COMPARE, value, withValue);
	}

	/**
	 * Performs calculation on given a and b arguments with given {@link IFunction}.
	 * 
	 * @throws RuntimeException if operation cannot be executed.
	 * 
	 * @param func
	 * @param a
	 * @param b
	 * @return
	 * @throws RuntimeException
	 */
	private Object calculate(IFunction func, Object a, Object b) throws RuntimeException{
		Object first = a == null ? Integer.valueOf(0) : a;
		Object second = b == null ? Integer.valueOf(0) : b;

		if(a instanceof String) {
			first = parse((String) a);
		}
		
		if(b instanceof String) {
			second = parse((String) b);
		}
		
		return func.execute(first, second);
	}

	/**
	 * Parses string to {@link Integer} if it can be done. If it can't be done,
	 * tries to parse it to {@link Double}. If that cannot be done too, throws
	 * exception.
	 *  
	 * @throws RuntimeException if given string cannot be parsed to integer or double. 
	 * 
	 * @param a
	 * @return parsed string as integer or double value
	 */
	private Object parse(String a) {
		if (((String) a).indexOf(".") != -1 || ((String) a).indexOf("E") != -1) {
			Double n;
			try {
				n = Double.parseDouble((String) a);
			} catch (NumberFormatException e) {
				throw new RuntimeException();
			}

			return n;
		} else {
			Integer n;
			try {
				n = Integer.parseInt((String) a);
			} catch (NumberFormatException e) {
				throw new RuntimeException();
			}

			return n;
		}
	}
}