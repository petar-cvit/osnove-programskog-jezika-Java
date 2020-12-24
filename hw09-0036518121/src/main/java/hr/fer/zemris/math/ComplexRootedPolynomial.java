package hr.fer.zemris.math;

/**
 * Represents polynomial with complex roots and it's constant.
 * Given polynomial looks like: f(z) = z0 * (z - z1) * (z - z2) * ... * (z - zn).
 * where z0 is constant and z1, z2, ..., zn are polynomial roots.
 * 
 * @author Petar Cvitanović
 *
 */
public class ComplexRootedPolynomial {
	
	/**
	 * polynomial constant
	 */
	private Complex constant;
	
	/**
	 * polynomial roots
	 */
	private Complex[] roots;
	
	/**
	 * Constructor with constant and array of roots.
	 * 
	 * @param constant
	 * @param roots
	 */
	public ComplexRootedPolynomial(Complex constant, Complex ... roots) {
		this.constant = constant;
		this.roots = roots;
	}
	
	/**
	 * Calculates polynomial for given point.
	 * 
	 * @param polynomial argument
	 * @return polynomial value in given point
	 */
	public Complex apply(Complex z) {		
		Complex out = constant;
		
		for(Complex c : roots) {
			out = out.multiply(z.sub(c));
		}
		
		return out;
	}
	
	/**
	 * Converts complex polynomial with roots and constant to complex polynomial
	 * that looks like: f(z) = zn * z^n + ... + z2 * z^2 + z1 * z + z0.
	 * 
	 * @return complex polynomial
	 */
	public ComplexPolynomial toComplexPolynom() {
		Complex[] factors = new Complex[roots.length + 1];
		Complex[] tmp = new Complex[roots.length + 1];
		
		for(int i = 0;i < factors.length;i++) {
			factors[i] = Complex.ZERO;
			tmp[i] = Complex.ZERO;
		}
		
		factors[0] = constant;
		
		for(int i = 0;i < roots.length;i++) {
			tmp = factors.clone();
			factors = shift(factors, i);
			tmp = multiplyAll(tmp, roots[i]);
			factors = subAll(factors, tmp);
		}
		
		return new ComplexPolynomial(factors);
		
	}
	
	/**
	 * Private method that subtracts two complex numbers arrays. 
	 * 
	 * @param first array
	 * @param second array
	 * @return new complex number array with values first[i] - second[i]
	 */
	private Complex[] subAll(Complex[] a, Complex[] b) {
		for(int i = 0;i < a.length;i++) {
			if(a[i] == null) {
				break;
			}
			
			a[i] = a[i].sub(b[i]);
		}
		
		return a;
	}
	
	/**
	 * Multiplies all complex number is given array with given complex number.
	 * 
	 * @param array
	 * @param number
	 * @return array with multiplied number
	 */
	private Complex[] multiplyAll(Complex[] array, Complex c) {
		for(int i = 0;i < array.length;i++) {
			if(array[i] == null) {
				break;
			}
			
			array[i] = array[i].multiply(c);
		}
		
		return array;
	}
	
	/**
	 * Shifts all complex number to the right from zero to given index.
	 * 
	 * @param array
	 * @param index
	 * @return shifted array
	 */
	private Complex[] shift(Complex[] array, int index) {
		for(int i = index;i >= 0;i--) {
			array[i + 1] = array[i];
		}
		
		array[0] = Complex.ZERO;
		
		return array;
	}
	
	/**
	 * Returns index of root that is the closest to given complex number only if difference between
	 * given number and root is less than given threshold. If there is no roots that have difference
	 * with given complex number less than threshold, method returns -1.
	 * 
	 * @param complex number
	 * @param treshold
	 * @return root index
	 */
	public int indexOfClosestRootFor(Complex z, double treshold) {
		int index = -1;
		
		for(int i = 0;i < roots.length;i++) {
			if(index == -1 && roots[i].sub(z).module() < treshold ||
					index != -1 && roots[index].sub(z).module() > roots[i].sub(z).module()) {
				index = i;
			}
		}
		
		return index;
	}
	
	@Override
	public String toString() {
		StringBuilder sb = new StringBuilder();
		
		sb.append(constant);
		
		for(Complex c : roots) {
			sb.append("*(z-" + c + ")");
		}
		
		return sb.toString();
	}
	
}