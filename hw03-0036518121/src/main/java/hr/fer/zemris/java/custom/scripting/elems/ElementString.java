package hr.fer.zemris.java.custom.scripting.elems;

/**
 * Holds string.
 * 
 * @author Petar Cvitanović
 *
 */
public class ElementString extends Element {
	
	/**
	 * string
	 */
	private String value;
	
	/**
	 * Constructor with string.
	 * 
	 * @param value
	 */
	public ElementString(String value) {
		this.value = value;
	}
	
	/**
	 * String getter.
	 * 
	 * @return string
	 */
	public String getValue() {
		return value;
	}
	
	@Override
	public String asText() {
		return value;
	}
	
	@Override
	public boolean equals(Object o) {
		if(!(o instanceof ElementString)) {
			return false;
		}
		
		ElementString node = (ElementString) o;
		
		return asText().equals(node.asText());
	}
}
