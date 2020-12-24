package hr.fer.zemris.java.custom.scripting.nodes;

import hr.fer.zemris.java.custom.scripting.elems.Element;
import hr.fer.zemris.java.custom.scripting.elems.ElementVariable;

/**
 * Extends Node class. Can have children of class TextNode and EchoNode.
 * It has 3-4 elements (variable, start expression, end expression, step expression(optional)).
 * If given more or less exception is thrown. First expression must be a variable.
 * 
 * @author Petar Cvitanović
 *
 */
public class ForLoopNode extends Node{

	/**
	 * variable
	 */
	private ElementVariable variable;
	
	/**
	 * start expression
	 */
	private Element startExpression;
	
	/**
	 * end expression
	 */
	private Element endExpression;
	
	/**
	 * step expression (can be null)
	 */
	private Element stepExpression;
	
	/**
	 * Constructor with elements.
	 * 
	 * @param variable
	 * @param startExpression
	 * @param endExpression
	 * @param stepExpression
	 */
	public ForLoopNode(ElementVariable variable, Element startExpression,
							Element endExpression, Element stepExpression) {
		this.variable = variable;
		this.startExpression = startExpression;
		this.endExpression = endExpression;
		this.stepExpression = stepExpression;
	}

	/**
	 * Variable getter.
	 * 
	 * @return variable
	 */
	public ElementVariable getVariable() {
		return variable;
	}

	/**
	 * Start expression getter
	 * 
	 * @return start expression
	 */
	public Element getStartExpression() {
		return startExpression;
	}

	/**
	 * End expression getter
	 * 
	 * @return end expression
	 */
	public Element getEndExpression() {
		return endExpression;
	}

	/**
	 * Step expression getter
	 * 
	 * @return step expression
	 */
	public Element getStepExpression() {
		return stepExpression;
	}
	
	@Override
	public boolean equals(Object o) {
		if(!(o instanceof ForLoopNode)) {
			return false;
		}
		
		ForLoopNode node = (ForLoopNode) o;
		
		if(!(getVariable().equals(node.getVariable()) &&
				getStartExpression().equals(node.getStartExpression()) &&
				getEndExpression().equals(node.getEndExpression()))) {
			return false;
		}
		
		if(!getStepExpression().equals(node.getStepExpression()) && getStepExpression() != null) {
			return false;
		}
		
		for(int i = 0;i < numberOfChildren();i++) {
			if(!getChild(i).equals(node.getChild(i))) {
				return false;
			}
		}
		
		return true;
	}
}

















