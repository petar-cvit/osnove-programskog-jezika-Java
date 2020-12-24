package hr.fer.zemris.java.hw01;

import java.util.Scanner;


/**
 * 
 * Class Factorial is used for calculating numbers from 0 to 20. However it uses a method 
 * that calculates factorials from 3 to 20.
 * 
 * @author Petar Cvitanović
 *
 */
public class Factorial {
		
	/**
	 * Method factorial, if given numbers from 3 to 20, returns number's factorial.
	 * If numbers are smaller than 3 or bigger than 20 method throws exception.
	 * 
	 * @param n
	 * @return factorial of n
	 */
	static long factorial(int n) {
		if(n > 20 || n < 0) {
			throw new IllegalArgumentException("'"+ n +"' nije broj u dozvoljenom rasponu.");
		}
		
		long rez = 1;
		for(int i = 1;i <= n;i++) {
			rez *= i;
		}
		
		return rez;
	}
	
	/**
	 * Method main runs when the program starts and calls method factorial.
	 * 
	 * @param args
	 */
	public static void main(String[] args) {
		Scanner sc = new Scanner(System.in);
		
		while(true) {
			System.out.printf("Unesite broj > ");
			if(sc.hasNextInt()) {
				int n = sc.nextInt();
				if(n > 20 || n < 3) {
					System.out.printf("'%d' nije broj u dozvoljenom rasponu.\n", n);
					continue;
				}
				long nFact = Long.valueOf(factorial(n));
				System.out.printf("%d! = %d\n", n, nFact);
			} else {
				String element = sc.next();
				if(element.equals("kraj")) {
					System.out.printf("Doviđenja.\n");
					break;
				}
				System.out.printf("'%s' nije cijeli broj.\n", element);
			}
		}
		
		sc.close();
	}
	
}









