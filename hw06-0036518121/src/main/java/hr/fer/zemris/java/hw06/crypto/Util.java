package hr.fer.zemris.java.hw06.crypto;

/**
 * Util class offers two methods. It's methods are used to convert bytes to hexadecimal values
 * in string form and the pther way.
 * 
 * @author Petar Cvitanović
 * 
 */
public class Util {
	
	/**
	 * Takes string of hexadecimal values. Method converts every pair of characters to
	 * byte values.
	 * 
	 * @throws IllegalArgumentException
	 * 				if given string is odd length or string contains arguments that are
	 * 				not hexadecimal digits.
	 * 
	 * @param keyText
	 * @return byte array
	 */
	public static byte[] hextobyte(String keyText) {
		if(keyText.length() % 2 != 0) {
			throw new IllegalArgumentException("Odd string lenght!");
		}
		
		byte[] out = new byte[keyText.length() / 2];
		
		keyText = keyText.toLowerCase();

		for(int i = 0;i < out.length;i++) {
			byte b = 0;
			
			b += getInt(keyText.charAt(i * 2)) * 16 + getInt(keyText.charAt(i * 2 + 1));
			
			out[i] = b;
		}
		
		return out;
	}
	
	/**
	 * For given char returns its integer value in decimal base.
	 * 
	 * @param character
	 * @return characters integer value
	 */
	private static int getInt(char c) {
		if(Character.isDigit(c)) {
			return Integer.parseInt(Character.toString(c));
		} else {

			switch (c) {
			case 'a':
				return 10;
			case 'b':
				return 11;
			case 'c':
				return 12;
			case 'd':
				return 13;
			case 'e':
				return 14;
			case 'f':
				return 15;

			default:
				throw new IllegalArgumentException("Illegal character: " + c);
			}
		}
	}
	
	/**
	 * Converts byte array to string. Every byte takes two hexadecimal digits in output string.
	 * 
	 * @param bytearray
	 * @return string
	 */
	public static String bytetohex(byte[] bytearray) {
		String out = "";
		
		for(byte b : bytearray) {
			String number = "";

			int n = b < 0 ? 256 : 0;

			number = number.concat(getHex((n + Integer.valueOf(b)) % 16));
			number = number.concat(getHex(((n + Integer.valueOf(b)) / 16) % 16));

			StringBuilder numberReversed = new StringBuilder();
			numberReversed.append(number);
			
			out = out.concat(numberReversed.reverse().toString());
		}
		
		return out;
	}
	
	/**
	 * Private method that converts numbers from decimal system to hexadecimal system. 
	 * 
	 * @throws IllegalArgumentException
	 * 				if given number is bigger than 15
	 * 
	 * @param n
	 * @return
	 */
	private static String getHex(Integer n) {
		if(n < 10) {
			return n.toString();
		}
		
		switch (n) {
		case 10:
			return "a";
		case 11:
			return "b";
		case 12:
			return "c";
		case 13:
			return "d";
		case 14:
			return "e";
		case 15:
			return "f";
		default:
			System.out.println(n);
			throw new IllegalArgumentException("Number bigger than 15!");
		}
	}

}


















