package hr.fer.zemris.java.custom.collections.demo;

import hr.fer.zemris.java.custom.collections.ArrayIndexedCollection;
import hr.fer.zemris.java.custom.collections.Collection;
import hr.fer.zemris.java.custom.collections.ElementsGetter;
import hr.fer.zemris.java.custom.collections.LinkedListIndexedCollection;

/**
 * Demo class for getter.
 * 
 * @author Petar Cvitanović
 *
 */
public class GetterDemo {
	public static void main(String[] args) {
		Collection<String> col1 = new ArrayIndexedCollection<String>();
		Collection<String> col2 = new LinkedListIndexedCollection<String>();
		col1.add("Ivo");
		col1.add("Ana");
		col1.add("Jasna");
		col2.add("Jasmina");
		col2.add("Štefanija");
		col2.add("Karmela");
		ElementsGetter<String> getter1 = col1.createElementsGetter();
		ElementsGetter<String> getter2 = col1.createElementsGetter();
		ElementsGetter<String> getter3 = col2.createElementsGetter();
		System.out.println("Jedan element: " + getter1.getNextElement());
		System.out.println("Jedan element: " + getter1.getNextElement());
		System.out.println("Jedan element: " + getter2.getNextElement());
		System.out.println("Jedan element: " + getter3.getNextElement());
		System.out.println("Jedan element: " + getter3.getNextElement());
		
		System.out.println(col1.contains("Ana")); //true
 		System.out.println(col1.contains("Marko")); //false
		System.out.println(col1.contains(3)); //false
		System.out.println(col1.contains(null)); //false
		
		System.out.println(col2.contains("Karmela")); //true
		System.out.println(col2.contains("Marko")); //false
		System.out.println(col2.contains(3)); //false
		System.out.println(col2.contains(null)); // false
		
		}
}
