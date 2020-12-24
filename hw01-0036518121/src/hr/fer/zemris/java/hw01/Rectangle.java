package hr.fer.zemris.java.hw01;

import java.util.Scanner;

/**
 * 
 * Class Rectangle is used to calculate the area and circumference of a rectangle defined
 * by height and width.
 * 
 * @author Petar Cvitanović
 *
 */
public class Rectangle {

	/**
	 * Method area returns area of a rectangle defined by height and width.
	 * 
	 * @param height
	 * @param width
	 * 
	 * @return area of a rectangle
	 */
	static double area(double height, double width) {
		return height * width;
	}

	/**
	 * Method circumference returns circumference of a rectangle defined by height and width.
	 * 
	 * @param height
	 * @param width
	 * 
	 * @return circumference of a rectangle
	 */
	static double circumference(double height, double width) {
		return 2 * height + 2 * width;
	}

	/**
	 * Method input is used for taking input from the keyboard. It returns
	 * first number it gets from keyboard.
	 * 
	 * @param string that is printed
	 * @param Scanner
	 * 
	 * @return number it gets as input from keyboard
	 */
	static double input(String str, Scanner sc) {
		double rez = 0;

		while(true) {
			System.out.print(str);
			String element = sc.nextLine().trim();
			try {
				rez = Double.parseDouble(element);
				if(rez <= 0) {
					System.out.println("Mora biti unesen pozitvan broj.");
				}
				if(rez > 0) {
					break;
				}
			} catch (NumberFormatException ex) {
				System.out.printf("'%s' se ne može protumačiti kao broj.\n", element);
			}
		}

		return rez;
	}

	/**
	 * The main program starts once the program starts running.
	 * It can get height and width through keyboard once the program starts running
	 * or through command line arguments.
	 * 
	 * @param args
	 */
	public static void main(String[] args) {
		double height = 0;
		double width = 0;
		boolean falseArguments = false;

		Scanner sc = new Scanner(System.in);
		
		if(args.length == 2) {
			try {
				height = Double.parseDouble(args[0]);
				width = Double.parseDouble(args[1]);
			} catch(IllegalArgumentException ex) {
				System.out.println("Argumenti se ne mogu protumačiti kao brojevi.");
				falseArguments = true;
			}
			if((height <= 0 || width <= 0) && !falseArguments) {
				System.out.println("Argumenti moraju biti pozitivni brojevi.");
				falseArguments = true;
			}
		}
		
		if(args.length != 0 && args.length != 2) {
			System.out.println("Broj argumenata nije zadovoljavajuć(nijedan ili dva).");
		} else if(!falseArguments){
			if(args.length == 0) {
				width = input("Unesite širinu > ", sc);
				height = input("Unesite visinu > ", sc);
			}
						
			System.out.printf("Pravokutnik širine %.1f i visine %.1f ima površinu %.1f te opseg %.1f.\n", 
					width, height, area(height, width), circumference(height, width));
		}
		
		sc.close();
	}
}
