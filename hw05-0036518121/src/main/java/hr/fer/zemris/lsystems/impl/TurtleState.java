package hr.fer.zemris.lsystems.impl;

import java.awt.Color;

import hr.fer.zemris.math.Vector2D;

/**
 * Turtle state class represents one state of the turtle with turtle's direction, position,
 * color of the line and a step that turtle is going to make.
 * 
 * @author Petar Cvitanović
 *
 */
public class TurtleState {
	
	/**
	 * vector that represents turtle's direction, has length one
	 */
	private Vector2D direction;
	
	/**
	 * vector that represents position of a turtle with it's head
	 */
	private Vector2D position;
	
	/**
	 * color of the line
	 */
	private Color color;
	
	/**
	 * step that turtle makes
	 */
	private double step;
	
	/**
	 * Constructor with direction vector, position vector, color and step.
	 * 
	 * @param direction
	 * @param position
	 * @param color
	 * @param step
	 */
	public TurtleState(Vector2D direction, Vector2D position, Color color, double step) {
		this.direction = direction;
		this.color = color;
		this.position = position;
		this.step = step;
	}
	
	/**
	 * Returns a copy of a turtle state.
	 * 
	 * @return TurtleState
	 */
	public TurtleState copy() {
		return new TurtleState(direction.copy(), position.copy(), color, step);
	}
	
	/**
	 * Direction getter.
	 * 
	 * @return direction
	 */
	public Vector2D getDirection() {
		return direction;
	}
	
	/**
	 * Position getter.
	 * 
	 * @return position
	 */
	public Vector2D getPosition() {
		return position;
	}
	
	/**
	 * Color getter.
	 * 
	 * @return color
	 */
	public Color getColor() {
		return color;
	}
	
	/**
	 * Step getter.
	 * 
	 * @return step
	 */
	public double getStep() {
		return step;
	}
	
	/**
	 * Direction setter
	 * 
	 * @param direction
	 */
	public void setDirection(Vector2D direction) {
		this.direction = direction;
	}
	
	/**
	 * Position setter.
	 * 
	 * @param position
	 */
	public void setPosition(Vector2D position) {
		this.position = position;
	}
	
	/**
	 * Color setter.
	 * 
	 * @param color
	 */
	public void setColor(Color color) {
		this.color = color;
	}
	
	/**
	 * Step setter.
	 * 
	 * @param s
	 */
	public void setStep(double step) {
		this.step = step;
	}
	
	/**
	 * Scales step with given scaler.
	 * 
	 * @param scaler
	 */
	public void scaleShift(double scaler) {
		step = step * scaler;
	}
}
