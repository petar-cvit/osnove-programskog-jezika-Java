package hr.fer.zemris.math;

import java.util.ArrayList;
import java.util.List;

/**
 * Represents complex numbers and contains methods to work with complex numbers.
 * 
 * @author Petar Cvitanović
 *
 */
public class Complex {

	/**
	 * real part of complex number
	 */
	private final double real;

	/**
	 * imaginary part of complex number
	 */
	private final double imaginary;

	/**
	 * Complex number with real and imaginary part equal to zero.
	 */
	public static final Complex ZERO = new Complex(0,0);

	/**
	 * Complex number with real part equal to one and imaginary part equal to zero.
	 */
	public static final Complex ONE = new Complex(1,0);

	/**
	 * Complex number with real part equal to negative one and imaginary part equal to zero.
	 */
	public static final Complex ONE_NEG = new Complex(-1,0);

	/**
	 * Complex number with real part equal to zero and imaginary part equal to one.
	 */
	public static final Complex IM = new Complex(0,1);

	/**
	 * Complex number with real part equal to zero and imaginary part equal to negative one.
	 */
	public static final Complex IM_NEG = new Complex(0,-1);

	/**
	 * Default constructor sets both values to zero.
	 */
	public Complex() {
		this.real = 0;
		this.imaginary = 0;
	}

	/**
	 * Constructor with real and imaginary parts as doubles.
	 * 
	 * @param real
	 * @param imaginary
	 */
	public Complex(double real, double imaginary) {

		this.real = real;
		this.imaginary = imaginary;
	}

	/**
	 * Constructor that takes string and parses it to complex number.
	 * 
	 * @param string to be parsed
	 */
	public Complex(String s) {
		s = s.replaceAll("\\s", "");

		if(s.indexOf("i") == -1) {
			real = Double.parseDouble(s);
			imaginary = 0;
		} else {
			String[] parts = s.split("i");
			
			switch (parts.length) {
			case 0:
				real = 0;
				imaginary = 1;
				
				break;
			case 1:
				real = parts[0].length() == 1 ? 0 : Double.parseDouble(
						parts[0].substring(0, parts[0].length() - 1));
				
				imaginary = Double.parseDouble(parts[0].substring(
						parts[0].length() - 1, parts[0].length()) + "1");
				
				break;
				
			case 2:
				if(parts[0].isBlank()) {
					real = 0;
					imaginary = Double.parseDouble(parts[1]);
				
				} else {
					real = parts[0].equals("-") ? 0 : 
						Double.parseDouble(
								parts[0].substring(0, parts[0].length() - 1));
					
					imaginary = Double.parseDouble(
							parts[0].substring(parts[0].length() - 1, parts[0].length()) + parts[1]);
				}
				
				break;
				
			default:
				throw new NumberFormatException();
					
			}
		}
	}

	/**
	 * Returns current number's real part.
	 * 
	 * @return number's real part
	 */
	public double getReal() {
		return real;
	}

	/**
	 * Returns current number's imaginary part.
	 * 
	 * @return number's imaginary part
	 */
	public double getImaginary() {
		return imaginary;
	}

	/**
	 * Returns current number's magnitude.
	 * 
	 * @return number's magnitude
	 */
	public double module() {
		return Math.sqrt(Math.pow(real, 2) + Math.pow(imaginary, 2));
	}

	/**
	 * Returns current number's angle.
	 * 
	 * @return number's angle
	 */
	private double getAngle() {	
		double angle = Math.atan2(imaginary, real);

		if(angle < 0) {
			angle += 2*Math.PI;
		}

		return angle;
	}

	/**
	 * Adds two complex numbers.
	 * 
	 * @param c
	 * @return Complex
	 */
	public Complex add(Complex c) {	
		return new Complex(this.real + c.getReal(),
				this.imaginary + c.getImaginary());
	}

	/**
	 * Subtracts two complex numbers.
	 * 
	 * @param c
	 * @return Complex
	 */
	public Complex sub(Complex c) {
		return new Complex(this.real - c.getReal(),
				this.imaginary - c.getImaginary());
	}

	/**
	 * Multiplies two complex numbers.
	 * 
	 * @param c
	 * @return Complex
	 */
	public Complex multiply(Complex c) {
		return new Complex(this.real * c.getReal() - this.imaginary * c.getImaginary(),
				this.real * c.getImaginary() + this.imaginary * c.getReal());
	}

	/**
	 * Divides two complex numbers.
	 * 
	 * @param c
	 * @return Complex
	 */
	public Complex divide(Complex c) {
		double divisor = Math.pow(c.getReal(), 2) + Math.pow(c.getImaginary(), 2);

		return new Complex((this.real * c.getReal() + this.imaginary * c.getImaginary())/divisor,
				(this.imaginary * c.getReal() - this.real * c.getImaginary())/divisor);
	}

	/**
	 * Returns a complex number to the power of n.
	 * 
	 * @param n
	 * @return
	 */
	public Complex power(int n) {
		return new Complex(Math.pow(module(), n) * Math.cos(getAngle() * n),
				Math.pow(module(), n) * Math.sin(getAngle() * n));
	}

	/**
	 * Returns an array of complex numbers. Numbers in that array are all
	 * n nth roots of a complex number.
	 * 
	 * @param n
	 * @return
	 */
	public List<Complex> root(int n) {	
		List<Complex> numbers = new ArrayList<Complex>();
		for(int i = 0;i < n;i++) {
			numbers.add(new Complex(Math.pow(module(), 1./n) * Math.cos((getAngle() + 2.*i*Math.PI)/n),
					Math.pow(module(), 1./n) * Math.sin((getAngle() + 2.*i*Math.PI)/n)));
		}

		return numbers;
	}

	public Complex negate() {
		return new Complex(-real, -imaginary);
	}

	/**
	 * Returns string that represents complex number.
	 */
	public String toString() {
		return String.format("(%.1f", real) +
				(imaginary < 0 ? "-" : "+") +
				String.format("i%.1f)", Math.abs(imaginary));
	}

}
