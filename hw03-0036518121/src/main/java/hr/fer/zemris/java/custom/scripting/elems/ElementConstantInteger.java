package hr.fer.zemris.java.custom.scripting.elems;

/**
 * Holds integer value.
 * 
 * @author Petar Cvitanović
 *
 */
public class ElementConstantInteger extends Element {

	/**
	 * element value
	 */
	private int value;
	
	/**
	 * Constructor with integer value.
	 * 
	 * @param int
	 */
	public ElementConstantInteger(int value) {
		this.value = value;
	}
	
	/**
	 * Value getter.
	 * 
	 * @return int
	 */
	public int getValue() {
		return value;
	}
	
	@Override
	public String asText() {
		return Integer.toString(value);
	}
	
	@Override
	public boolean equals(Object o) {
		if(!(o instanceof ElementConstantInteger)) {
			return false;
		}
		
		ElementConstantInteger node = (ElementConstantInteger) o;
		
		return getValue() == node.getValue();
	}
}
