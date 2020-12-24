package hr.fer.zemris.java.custom.collections.demo;

import hr.fer.zemris.java.custom.collections.EmptyStackException;
import hr.fer.zemris.java.custom.collections.ObjectStack;

/**
 * Class StackDemo is used to evaluate postfix expressions. It uses ObjectStack.
 * Main method takes one argument from command line.
 * 
 * @author Petar Cvitanović
 *
 */
public class StackDemo {
	public static void main(String[] args) {
		if(args.length != 1) {
			System.out.println("Broj argumenata mora biti jedan!");
			return;
		}
		
		String[] array = args[0].split(" ");
		
		ObjectStack stack = new ObjectStack();
		boolean falseArgument = false, zeroDiv = false;
		
		for(String string : array) {
			if(string.isEmpty()) {
				continue;
			}
			
			try {
				Integer number = Integer.parseInt(string);
				stack.push(number);
			} catch (NumberFormatException numberFormat) {
				try {
					switch (string) {
						case "*":
							int firstMull = (int) stack.pop();
							int secondMull = (int) stack.pop();
							stack.push(firstMull * secondMull);
							break;
						
						case "+":
							int firstAdd = (int) stack.pop();
							int secondAdd = (int) stack.pop();
							stack.push(firstAdd + secondAdd);
							break;
							
						case "-":
							int firstSub = (int) stack.pop();
							int secondSub = (int) stack.pop();
							stack.push(secondSub - firstSub);
							break;
							
						case "/":
							int firstDiv = (int) stack.pop();
							int secondDiv = (int) stack.pop();
							stack.push(secondDiv / firstDiv);
							break;
							
						case "%":
							int firstMod = (int) stack.pop();
							int secondMod = (int) stack.pop();
							stack.push(secondMod % firstMod);
							break;
							
						default:
							falseArgument = true;
					}
				} catch (EmptyStackException emptyStack) {
					System.out.println(emptyStack.getMessage());
				} catch (ArithmeticException aritmetic) {
					System.out.println("Division with 0!");
					zeroDiv = true;
				}
			}
			if(falseArgument || stack.isEmpty()) {
					if(falseArgument) {
						System.out.println("Argument " + string + " is not acceptable!");
					}
				break;
			}
		}
		
		if(!falseArgument && !zeroDiv && !stack.isEmpty()) {
			if(stack.size() != 1) {
				System.out.println("Stack size is different than 1!");
			} else {
				System.out.println("Expression evaluates to " + stack.pop()+".");
			}
		}
	}
}
















