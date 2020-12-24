package hr.fer.zemris.java.custom.scripting.elems;

/**
 * Holds name of the function.
 * 
 * @author Petar Cvitanović
 *
 */
public class ElementFunction extends Element {

	/**
	 * function name
	 */
	private String name;
	
	/**
	 * Constructor with function's name.
	 * 
	 * @param name
	 */
	public ElementFunction(String name) {
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
		if(!(o instanceof ElementFunction)) {
			return false;
		}
		
		ElementFunction node = (ElementFunction) o;
		
		return asText().equals(node.asText());
	}
}
