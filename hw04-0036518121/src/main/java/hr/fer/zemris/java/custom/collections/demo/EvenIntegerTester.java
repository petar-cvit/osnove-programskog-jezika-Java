package hr.fer.zemris.java.custom.collections.demo;

import hr.fer.zemris.java.custom.collections.Tester;

/**
 * Tests even numbers.
 * 
 * @author Petar Cvitanović
 *
 */
class EvenIntegerTester<T> implements Tester<T> {
	
	/**
	 * Returns true if number is even.
	 */
	public boolean test(T obj) {
		if(!(obj instanceof Integer)) return false;
		Integer i = (Integer)obj;
		return i % 2 == 0;
	}
	
	public static void main(String[] args) {
		Tester<Integer> t = new EvenIntegerTester<Integer>();
		System.out.println(t.test(22));
		System.out.println(t.test(3));
	}
}