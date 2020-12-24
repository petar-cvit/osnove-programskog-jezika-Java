package hr.fer.zemris.math;

/**
 * Represents vectors in three dimensions.
 * 
 * @author Petar Cvitanović
 *
 */
public class Vector3 {

	/**
	 * vector's x component
	 */
	private final double x;
	
	/**
	 * vector's y component
	 */
	private final double y;
	
	/**
	 * vector's z component
	 */
	private final double z;

	/**
	 * Constructor with x, y and z components.
	 * 
	 * @param x
	 * @param y
	 * @param z
	 */
	public Vector3(double x, double y, double z) {
		this.x = x;
		this.y = y;
		this.z = z;
	}

	/**
	 * Calculates vectors length.
	 * 
	 * @return length
	 */
	public double norm() {
		return Math.sqrt(Math.pow(x, 2) + Math.pow(y, 2) +  Math.pow(z, 2));
	}

	/**
	 * Normalises vector.
	 * Returned vector has same direction, but it' length is one.
	 * 
	 * @return normalised vector
	 */
	public Vector3 normalized() {
		double length = norm();
		return new Vector3(x / length, y / length, z / length);
	}

	/**
	 * Adds two vectors.
	 * 
	 * @param other
	 * @return vectors that obtained by adding two vectors
	 */
	public Vector3 add(Vector3 other) {
		return new Vector3(this.x + other.x, this.y + other.y, this.z + other.z);
	}

	/**
	 * Subtracts two vectors.
	 * 
	 * @param other
	 * @return vectors that obtained by subtracting two vectors
	 */
	public Vector3 sub(Vector3 other) {
		return new Vector3(this.x - other.x, this.y - other.y, this.z - other.z);
	}

	/**
	 * Returns scalar product of this and given vectors.
	 * 
	 * @param other
	 * @return scalar product = this * other
	 */
	public double dot(Vector3 other) {
		return this.x * other.x + this.y * other.y + this.z * other.z; 
	}

	/**
	 * Returns vector product of this and given vectors.
	 * 
	 * @param other
	 * @return vector product = this X other
	 */
	public Vector3 cross(Vector3 other) {
		return new Vector3(this.y * other.z - this.z * other.y,
				this.z * other.x - this.x * other.z,
				this.x * other.y - this.y * other.x
				);
	}

	/**
	 * Returns new vector that is multiplied with given scalar.
	 * 
	 * @param scalar
	 * @return scaled vector
	 */
	public Vector3 scale(double s) {
		return new Vector3(this.x * s, this.y * s, this.z * s);
	}

	/**
	 * Calculates cosine of angle between this and given vector.
	 * 
	 * @param other
	 * @return angle cosine
	 */
	public double cosAngle(Vector3 other) {
		return (this.x * other.x + this.y * other.y + this.z * other.z) / (this.norm() * other.norm());
	}

	/**
	 * X component getter.
	 * 
	 * @return x component
	 */
	public double getX() {
		return x;
	}

	/**
	 * Y component getter.
	 * 
	 * @return y component
	 */
	public double getY() {
		return y;
	}

	/**
	 * Z component getter.
	 * 
	 * @return z component
	 */
	public double getZ() {
		return z;
	}

	/**
	 * Returns array of vectors components.
	 * 
	 * @return components in an array
	 */
	public double[] toArray() {
		return new double[] {this.x, this.y, this.z};
	}

	@Override
	public String toString() {
		return "(" + String.format("%.6f", x) + ", "
				+ String.format("%.6f", y) + ", "
				+ String.format("%.6f", z) + ")";
	}

}
