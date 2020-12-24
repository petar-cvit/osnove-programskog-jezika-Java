package hr.fer.zemris.java.custom.collections.demo;

import hr.fer.zemris.java.custom.collections.ArrayIndexedCollection;
import hr.fer.zemris.java.custom.collections.Collection;
import hr.fer.zemris.java.custom.collections.LinkedListIndexedCollection;
import hr.fer.zemris.java.custom.collections.List;

/**
 * Demo class for List implementation.
 * 
 * @author Petar Cvitanović
 *
 */
public class ListDemo {
	public static void main(String[] args) {
		List<String> col1 = new ArrayIndexedCollection<String>();
		List<String> col2 = new LinkedListIndexedCollection<String>();
		col1.add("Ivana");
		col2.add("Jasna");
		Collection<String> col3 = col1;
		Collection<String> col4 = col2;
		col1.get(0);
		col2.get(0);
		//col3.get(0); // neće se prevesti! Razumijete li zašto?
		//col4.get(0); // neće se prevesti! Razumijete li zašto?
		col1.forEach(System.out::println); // Ivana
		col2.forEach(System.out::println); // Jasna
		col3.forEach(System.out::println); // Ivana
		col4.forEach(System.out::println); // Jasna
		
		System.out.println(col1.indexOf("Ivana"));
		System.out.println(col1.indexOf("Luka"));
		System.out.println(col1.indexOf(2));
		System.out.println(col1.indexOf(null));
		
		System.out.println(col2.indexOf("Jasna"));
		System.out.println(col2.indexOf("Luka"));
		System.out.println(col2.indexOf(4));
		System.out.println(col2.indexOf(null));
	}
}
