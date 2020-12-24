package hr.fer.zemris.math;

/**
 * Represents complex polynomial that looks like: f(z) = zn * z^n + ... + z2 * z^2 + z1 * z + z0.
 * 
 * @author Petar Cvitanović
 *
 */
public class ComplexPolynomial {
	
	/**
	 * polynomial factors
	 */
	private Complex[] factors;
	
	/**
	 * Constructor with array of polynomial factors.
	 * 
	 * @param factors
	 */
	public ComplexPolynomial(Complex... factors) {
		this.factors = factors;
	}
	
	/**
	 * Returns polynomial order.
	 * 
	 * @return polynomial order
	 */
	public short order() {
		return (short) (factors.length - 1);
	}
	
	/**
	 * Multiplies two complex polynomials.
	 * 
	 * @param other polynomial
	 * @return multiplied polynomial
	 */
	public ComplexPolynomial multiply(ComplexPolynomial p) {
		int length = p.factors.length + this.factors.length - 1;
		
		Complex[] factors = new Complex[length];
		
		for(int i = 0;i < length;i++) {
			factors[i] = new Complex();
		}
		
		for(int i = 0;i < this.factors.length;i++) {
			for(int j = 0;j < p.factors.length;j++) {
				factors[i + j] = factors[i + j].add(this.factors[i].multiply(p.factors[j]));
			}
		}
		
		return new ComplexPolynomial(factors);
	}

	/**
	 * Returns derived polynomial.
	 * From given f(z) = z2 * z^2 + z1 * z + z0 returns new polynomial:
	 * f(z) = 2 * z2 * z + z1
	 * 
	 * @return
	 */
	public ComplexPolynomial derive() {
		Complex[] newFactors = new Complex[this.factors.length - 1];
		
		for(int i = 1;i < factors.length;i++) {
			newFactors[i - 1] = new Complex(factors[i].getReal() * i, factors[i].getImaginary() * i);
		}
		
		return new ComplexPolynomial(newFactors);
	}
	
	/**
	 * Calculates polynomial for given point.
	 * 
	 * @param polynomial argument
	 * @return polynomial value in given point
	 */
	public Complex apply(Complex z) {
		Complex c = factors[0];
		
		for(int i = 1;i < factors.length;i++) {
			c = c.add(factors[i].multiply(z.power(i)));
		}
		
		return c;
	}

	@Override
	public String toString() {
		StringBuilder sb = new StringBuilder();
		
		for(int i = factors.length - 1;i > 0;i--) {
			sb.append(factors[i] + "*z^" + i + "+");
		}
		
		sb.append(factors[0]);
		
		return sb.toString();
	}
}
