package hr.fer.zemris.math;

public class Vector2D {

	private double x;
	
	private double y;
	
	public Vector2D(double x, double y) {
		this.x = x;
		this.y = y;
	}
	
	public double getX() {
		return x;
	}
	
	public double getY() {
		return y;
	}
	
	public double getLength() {
		return Math.sqrt(Math.pow(x, 2) + Math.pow(y, 2));
	}
	
	public double getAngle() {
		double angle = Math.atan2(y, x);
		if(angle < 0) {
			angle += 2 * Math.PI;
		}
		
		return angle;
	}
	
	public void translate(Vector2D offset) {
		x += offset.getX();
		y += offset.getY();
	}
	
	public Vector2D translated(Vector2D offset) {
		Vector2D newVector = new Vector2D(x, y);
		
		newVector.translate(offset);
		return newVector;
	}
	
	public void rotate(double angle) {
		double oldAngle = getAngle();
		
		double length = getLength();
		
		oldAngle += angle;
		oldAngle = oldAngle % (2 * Math.PI);
		
		this.x = length * Math.cos(oldAngle);
		this.y = length * Math.sin(oldAngle);
	}
	
	public Vector2D rotated(double angle) {
		Vector2D newVector = new Vector2D(x, y);
		newVector.rotate(angle);
		
		return newVector;
	}
	
	public void scale(double scaler) {
		double angle = getAngle();
		
		double length = getLength() * scaler;
		
		x = length * Math.cos(angle);
		y = length * Math.sin(angle);
	}
	
	public Vector2D scaled(double scaler) {
		Vector2D newVector = new Vector2D(x, y);
		newVector.scale(scaler);
		
		return newVector;
	}
	
	public Vector2D copy() {
		return new Vector2D(x, y);
	}
}
