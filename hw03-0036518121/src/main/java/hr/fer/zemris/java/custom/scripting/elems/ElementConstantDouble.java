package hr.fer.zemris.java.custom.scripting.elems;

/**
 * Holds double value.
 * 
 * @author Petar Cvitanović
 *
 */
public class ElementConstantDouble extends Element{
	
	/**
	 * element value
	 */
	private double value;
	
	/**
	 * Constructor with double value.
	 * 
	 * @param double
	 */
	public ElementConstantDouble(double value) {
		this.value = value;
	}
	
	/**
	 * Value getter.
	 * 
	 * @return double
	 */
	public double getValue() {
		return value;
	}
	
	@Override
	public String asText() {
		return Double.toString(value);
	}
	
	@Override
	public boolean equals(Object o) {
		if(!(o instanceof ElementConstantDouble)) {
			return false;
		}
		
		ElementConstantDouble node = (ElementConstantDouble) o;
		
		return getValue() == node.getValue();
	}
}
