package hr.fer.zemris.lsystems.impl;

import hr.fer.zemris.java.custom.collections.ObjectStack;

/**
 * Context class holds instances of TurtleState in a stack.
 * 
 * @author Petar Cvitanović
 *
 */
public class Context {

	/**
	 * stack with instances of TurtleStates
	 */
	private ObjectStack<TurtleState> turtleStates;
	
	/**
	 * default constructor
	 */
	public Context() {
		turtleStates = new ObjectStack<TurtleState>();
	}
	
	/**
	 * Returns state from the top of the stack.
	 * 
	 * @return turtle state
	 */
	public TurtleState getCurrentState() {
		return turtleStates.peek();
	}
	
	/**
	 * Pushes given state on the stack.
	 * 
	 * @param state
	 */
	public void pushState(TurtleState state) {
		turtleStates.push(state);
	}
	
	/**
	 * Pops one state from the stack.
	 */
	public void popState() {
		turtleStates.pop();
	}
}
