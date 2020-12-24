package hr.fer.zemris.math;

/**
 * Vector2D class represents a vector with its components x and y. Vector has methods for scalar multiplication, 
 * rotating and translation.
 * 
 * @author Petar Cvitanović
 *
 */
public class Vector2D {

	/**
	 * vector's x component
	 */
	private double x;
	
	/**
	 * vector's y components
	 */
	private double y;
	
	/**
	 * Constructor with x and y components.
	 * 
	 * @param x
	 * @param y
	 */
	public Vector2D(double x, double y) {
		this.x = x;
		this.y = y;
	}
	
	/**
	 * Returns vector's x component.
	 * 
	 * @return x component
	 */
	public double getX() {
		return x;
	}
	
	/**
	 * Returns vector's x component.
	 * 
	 * @return x component
	 */
	public double getY() {
		return y;
	}
	
	/**
	 * Private method that returns vector's length.
	 * 
	 * @return length
	 */
	private double getLength() {
		return Math.sqrt(Math.pow(x, 2) + Math.pow(y, 2));
	}
	
	/**
	 * Private method that returns vector's angle relative to the positive direction of x axis.
	 * Angle is between 0 and 2 pi.
	 * 
	 * @return angle
	 */
	private double getAngle() {
		double angle = Math.atan2(y, x);
		if(angle < 0) {
			angle += 2 * Math.PI;
		}
		
		return angle;
	}
	
	/**
	 * Translates vector's head for given vector for another vector.
	 * 
	 * @param offset
	 */
	public void translate(Vector2D offset) {
		x += offset.getX();
		y += offset.getY();
	}
	
	/**
	 * Returns a vector obtained by translating the head of this vector with another.
	 * 
	 * @param offset
	 * @return translated vector
	 */
	public Vector2D translated(Vector2D offset) {
		Vector2D newVector = new Vector2D(x, y);
		
		newVector.translate(offset);
		return newVector;
	}
	
	/**
	 * Rotates vector for given angle.
	 * 
	 * @param angle
	 */
	public void rotate(double angle) {
		double oldAngle = getAngle();
		
		double length = getLength();
		
		oldAngle += angle;
		oldAngle = oldAngle % (2 * Math.PI);
		
		this.x = length * Math.cos(oldAngle);
		this.y = length * Math.sin(oldAngle);
	}
	
	/**
	 * Returns a vector obtained by rotating this vector for given angle.
	 * 
	 * @param angle
	 * @return
	 */
	public Vector2D rotated(double angle) {
		Vector2D newVector = new Vector2D(x, y);
		newVector.rotate(angle);
		
		return newVector;
	}
	
	/**
	 * Scales vector for a given number.
	 * 
	 * @param scaler
	 */
	public void scale(double scaler) {
		double angle = getAngle();
		
		double length = getLength() * scaler;
		
		x = length * Math.cos(angle);
		y = length * Math.sin(angle);
	}
	
	/**
	 * Returns a vector obtained by scaling this vector with given scaler.
	 * 
	 * @param scaler
	 * @return
	 */
	public Vector2D scaled(double scaler) {
		Vector2D newVector = new Vector2D(x, y);
		newVector.scale(scaler);
		
		return newVector;
	}
	
	/**
	 * Returns vector with same x and y components.
	 * 
	 * @return copy of this vector
	 */
	public Vector2D copy() {
		return new Vector2D(x, y);
	}
}


















