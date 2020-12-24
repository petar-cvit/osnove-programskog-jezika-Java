package hr.fer.zemris.java.custom.collections.demo;

import hr.fer.zemris.java.custom.collections.Tester;

/**
 * Tests even numbers.
 * 
 * @author Petar Cvitanović
 *
 */
class EvenIntegerTester implements Tester {
	
	/**
	 * Returns true if number is even.
	 */
	public boolean test(Object obj) {
		if(!(obj instanceof Integer)) return false;
		Integer i = (Integer)obj;
		return i % 2 == 0;
	}
	
	public static void main(String[] args) {
		Tester t = new EvenIntegerTester();
		System.out.println(t.test("Ivo"));
		System.out.println(t.test(22));
		System.out.println(t.test(3));
	}
}