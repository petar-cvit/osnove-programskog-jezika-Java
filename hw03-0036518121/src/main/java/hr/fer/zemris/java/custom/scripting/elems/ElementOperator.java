package hr.fer.zemris.java.custom.scripting.elems;

/**
 * Holds operator.
 * 
 * @author Petar Cvitanović
 *
 */
public class ElementOperator extends Element {
	
	/**
	 * operator symbol
	 */
	private String symbol;
	
	/**
	 * Constructor with operator symbol.
	 * 
	 * @param symbol
	 */
	public ElementOperator(String symbol) {
		this.symbol = symbol;
	}
	
	/**
	 * Symbol Getter.
	 * 
	 * @return symbol
	 */
	public String getSymbol() {
		return symbol;
	}
	
	@Override
	public String asText() {
		return symbol;
	}
	
	@Override
	public boolean equals(Object o) {
		if(!(o instanceof ElementOperator)) {
			return false;
		}
		
		ElementOperator node = (ElementOperator) o;
		
		return asText().equals(node.asText());
	}
}
