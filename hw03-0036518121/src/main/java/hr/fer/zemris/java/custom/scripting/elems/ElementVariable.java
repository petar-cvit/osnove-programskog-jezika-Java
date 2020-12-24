package hr.fer.zemris.java.custom.scripting.elems;

/**
 * Holds variable
 * 
 * @author Petar Cvitanović
 *
 */
public class ElementVariable extends Element {

	/**
	 * variable name
	 */
	private String name;
	
	/**
	 * Constructor with variable name.
	 * 
	 * @param name
	 */
	public ElementVariable(String name) {
		this.name = name;
	}
	
	/**
	 * Name getter.
	 * 
	 * @return name
	 */
	public String getName() {
		return name;
	}
	
	@Override
	public String asText() {
		return name;
	}
	
	@Override
	public boolean equals(Object o) {
		if(!(o instanceof ElementVariable)) {
			return false;
		}
		
		ElementVariable node = (ElementVariable) o;
		
		return asText().equals(node.asText());
	}
}
