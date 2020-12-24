package hr.fer.zemris.java.custom.scripting.nodes;

import java.util.PrimitiveIterator.OfDouble;

import hr.fer.zemris.java.custom.collections.ArrayIndexedCollection;

/**
 * Node class is a base class for all nodes.
 * 
 * @author Petar Cvitanović
 *
 */
public class Node {
	
	/**
	 * collection of children
	 */
	ArrayIndexedCollection children;
	
	/**
	 * Adds a child to children collection
	 * 
	 * @throws NullPointerException
	 * 				if given child is null
	 * 
	 * @param child
	 */
	public void addChildNode(Node child) {
		if(child == null) {
			throw new NullPointerException("Child cannot be null!");
		}
		
		if(children == null) {
			children = new ArrayIndexedCollection();
		}
		
		children.add(child);
	}
	
	/**
	 * Returns a number of direct children.
	 * 
	 * @return number of children
	 */
	public int numberOfChildren() {
		return children.size();
	}
	
	/**
	 * Returns selected child.
	 * 
	 * @throws IndexOutOfBoundsException
	 * 				if index is less than 0 or bigger than number of children - 1.
	 * 
	 * @param index
	 * @return child node
	 */
	public Node getChild(int index) {
		if(index < 0 || index > children.size()-1) {
			throw new IndexOutOfBoundsException("Invalid child index!");
		}
		
		return (Node) children.get(index);
	}
	
}
