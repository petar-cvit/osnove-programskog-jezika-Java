package hr.fer.zemris.java.hw02;

/**
 * Complex number class represents complex numbers with real and 
 * imaginary part. Class instances can perform arithmetic operations.
 * 
 * @author Petar Cvitanović
 *
 */
public class ComplexNumber {
	
	/**
	 * real part of complex number
	 */
	private double real;
	
	/**
	 * imaginary part of complex number
	 */
	private double imaginary;
	
	/**
	 * Constructor with real and imaginary parts as doubles.
	 * 
	 * @param real
	 * @param imaginary
	 */
	public ComplexNumber(double real, double imaginary) {
		this.real = real;
		this.imaginary = imaginary;
	}
	
	/**
	 * Factory method for a complex number without imaginary part.
	 * 
	 * @param real
	 * @return ComplexNumber
	 */
	public static ComplexNumber fromReal(double real) {
		return new ComplexNumber(real, 0.);
	}
	
	/**
	 * Factory method for a complex number without real part.
	 * 
	 * @param real
	 * @return ComplexNumber
	 */
	public static ComplexNumber fromImaginary(double imaginary) {
		return new ComplexNumber(0., imaginary);
	}
	
	/**
	 * Factory method for a complex number using number's magnitude
	 * and angle.
	 * 
	 * @param real
	 * @return ComplexNumber
	 */
	public static ComplexNumber fromMagnitudeAndAngle(double magnitude, double angle) {
		return new ComplexNumber(magnitude * Math.cos(angle), magnitude * Math.sin(angle));
	}
	
	/**
	 * Factory method that creates a complex number from string.
	 * 
	 * @param s
	 * @return ComplexNumber
	 */
	public static ComplexNumber parse(String s) {
		double real = 0;
		double imaginary = 0;
		int firstSign = 1;
		int secondSign = 1;
		String[] numbers = null;
		boolean change = false;
		
		String number = s;
		
		if(s.indexOf("-") == 0) {
			firstSign = -1;
			s = s.substring(1);
			change = true;
		} else if(s.indexOf("+") == 0 && !change) {
			s = s.substring(1);
		}
		try {
			if(s.indexOf("i") == s.length() - 1) {
				s = s.substring(0, s.indexOf("i"));
				if(s.contains("-") || s.contains("+")) {	
					if(s.contains("-")) {
						numbers = s.split("-");
						secondSign = -1;
					} else if(s.contains("+")){
						numbers = s.split("\\+");
					}
					if(numbers[0].contains("+") || numbers[0].contains("-") || 
							numbers[1].contains("+") || numbers[1].contains("-")) {
						throw new NumberFormatException();
					}
					if(numbers[0].equals(null)) {
						imaginary = Double.parseDouble(numbers[1]) * firstSign;
					} else {
						real = Double.parseDouble(numbers[0]) * firstSign;
						imaginary = Double.parseDouble(numbers[1]) * secondSign;
					}
				} else {
					real = 0;
					if(s.isEmpty()) {
						imaginary = firstSign;
					} else {
						imaginary = Double.parseDouble(s) * firstSign;
					}
				}
			} else {
				if(s.contains("+") || s.contains("-")) {
					throw new NumberFormatException();
				}
				real = Double.parseDouble(s) * firstSign;
				imaginary = 0;
			}
		} catch (NumberFormatException ex) {
			throw new NumberFormatException("String '" + number + "' string cannot be interpreted as a number");
		}
		return new ComplexNumber(real, imaginary);
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
	public double getMagnitude() {
		return Math.sqrt(real * real + imaginary * imaginary);
	}
	
	/**
	 * Returns current number's angle.
	 * 
	 * @return number's angle
	 */
	public double getAngle() {	
		double angle = Math.atan2(imaginary, real);
		
		/*if(imaginary < 0 && real < 0){
			angle = angle - Math.PI;
		} else if(real < 0) {
			angle = Math.PI + angle;
		}*/
		
		if(angle < 0) {
			angle += 2*Math.PI;
		}

		return angle;
	}
	
	/**
	 * Adds two complex numbers.
	 * 
	 * @param c
	 * @return ComplexNumber
	 */
	public ComplexNumber add(ComplexNumber c) {	
		return new ComplexNumber(real + c.getReal(), imaginary + c.getImaginary());
	}
	
	/**
	 * Subtracts two complex numbers.
	 * 
	 * @param c
	 * @return ComplexNumber
	 */
	public ComplexNumber sub(ComplexNumber c) {
		double real = this.real - c.getReal();
		double imaginary = this.imaginary - c.getImaginary();
		return new ComplexNumber(real, imaginary);
	}
	
	/**
	 * Multiplies two complex numbers.
	 * 
	 * @param c
	 * @return ComplexNumber
	 */
	public ComplexNumber mul(ComplexNumber c) {
		return new ComplexNumber(this.real * c.getReal() - this.imaginary * c.getImaginary(),
				this.real * c.getImaginary() + this.imaginary * c.getReal());
	}
	
	/**
	 * Divides two complex numbers.
	 * 
	 * @param c
	 * @return ComplexNumber
	 */
	public ComplexNumber div(ComplexNumber c) {
		double divisor = Math.pow(c.getReal(), 2) + Math.pow(c.getImaginary(), 2);
		return new ComplexNumber((this.real * c.getReal() + this.imaginary * c.getImaginary())/divisor,
				(this.imaginary * c.getReal() - this.real * c.getImaginary())/divisor);
	}
	
	/**
	 * Returns a complex number to the power of n.
	 * 
	 * @param n
	 * @return
	 */
	public ComplexNumber power(int n) {
		return new ComplexNumber(Math.pow(getMagnitude(), n) * Math.cos(getAngle() * n),
				Math.pow(getMagnitude(), n) * Math.sin(getAngle() * n));
	}
	
	/**
	 * Returns an array of complex numbers. Numbers in that array are all
	 * n nth roots of a complex number.
	 * 
	 * @param n
	 * @return
	 */
	public ComplexNumber[] root(int n) {	
		ComplexNumber[] numbers = new ComplexNumber[n];
		for(int i = 0;i < n;i++) {
			numbers[i] = new ComplexNumber(Math.pow(getMagnitude(), 1./n) * Math.cos((getAngle() + 2.*i*Math.PI)/n),
					Math.pow(getMagnitude(), 1./n) * Math.sin((getAngle() + 2.*i*Math.PI)/n));
		}
		
		return numbers;
	}
	
	/**
	 * Returns string that represents complex number.
	 */
	public String toString() {
		if(real == 0) {
			return String.format("%.2fi", imaginary);
		} else if(imaginary == 0) {
			return String.format("%.2f", real);
		}
		if(imaginary > 0) {
			return String.format("%f+%fi", real, imaginary);
		} else {
			return String.format("%f%fi", real, imaginary);
		}
	}
}