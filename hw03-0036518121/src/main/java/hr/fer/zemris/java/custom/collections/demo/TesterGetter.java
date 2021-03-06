package hr.fer.zemris.java.custom.collections.demo;

import hr.fer.zemris.java.custom.collections.ArrayIndexedCollection;
import hr.fer.zemris.java.custom.collections.Collection;
import hr.fer.zemris.java.custom.collections.LinkedListIndexedCollection;

/**
 * Demo class for Tester and getter.
 * 
 * @author Petar Cvitanović
 *
 */
public class TesterGetter {
	public static void main(String[] args) {
		Collection col2 = new ArrayIndexedCollection();
		Collection col1 = new LinkedListIndexedCollection();
		col1.add(2);
		col1.add(3);
		col1.add(4);
		col1.add(5);
		col1.add(6);
		col2.add(12);
		col2.addAllSatisfying(col1, new EvenIntegerTester());
		col2.forEach(System.out::println);
	}
}
