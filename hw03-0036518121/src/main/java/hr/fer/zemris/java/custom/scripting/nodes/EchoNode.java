package hr.fer.zemris.java.custom.scripting.nodes;

import hr.fer.zemris.java.custom.scripting.elems.Element;

/**
 * Echo node class extends node class. It holds elements in that are in tags whose name isn't for.
 * Can't have children.
 * 
 * @author Petar Cvitanović
 *
 */
public class EchoNode extends Node {

	/**
	 * elements in tag
	 */
	private Element[] elements;
	
	/**
	 * Constructor with element array.
	 * 
	 * @throws NullPointerException
	 * 				if given elements array is null
	 * 
	 * @param elements
	 */
	public EchoNode(Element[] elements) {
		if(elements == null) {
			throw new NullPointerException("Elements can' be null!");
		}
		this.elements = elements;
	}

	/**
	 * Elements getter.
	 * 
	 * @return node's elements
	 */
	public Element[] getElements() {
		return elements;
	}
	
	@Override
	public boolean equals(Object o) {
		if(!(o instanceof EchoNode)) {
			return false;
		}
		
		EchoNode node = (EchoNode) o;
		
		for(int i = 0;i < elements.length;i++) {
			if(!elements[i].equals(node.getElements()[i])) {
				return false;
			}
		}
		
		return true;
	}
}
