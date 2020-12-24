package hr.fer.zemris.java.hw06.crypto;
 
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.security.spec.AlgorithmParameterSpec;
import java.util.Scanner;

import javax.crypto.BadPaddingException;
import javax.crypto.Cipher;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.SecretKeySpec;

/**
 * Class used for calculating file's sha-256, encryption and decryption. For calculating sha-256
 * this class uses MessageDigest class and it's methods. For encryption and decryption class
 * uses Cipher class.
 * 
 * @author Petar Cvitanović
 *
 */
public class Crypto {

	/**
	 * Main method takes arguments from command line. If first argument is "checksha" method takes
	 * in one more argument. That argument is file path whose sha-256 needs to be calculated.
	 * User inputs some sha-256 which is compared with calculated sha-256. If calculated sha-256
	 * is same as given string, program writes appropriate message. If calculated and given 
	 * are different program writes out calculated sha-256. If first argument is "encrypt" or
	 * "decrypt", method takes in two more arguments. First argument is the source file's, and
	 * second argument is destination file's path. Based on first argument program decrypt's
	 * or encrypt's from source file to destination file.
	 * 
	 * @param args
	 */
	public static void main(String[] args) {
		Scanner sc = new Scanner(System.in);

		try {
			switch (args[0]) {
			case "checksha":
				if(args.length != 2) {
					System.out.println("Invalid number of elements!");
					sc.close();
					return;
				}

				sha256(args[1], sc);
				break;

			case "encrypt":
				if(args.length != 3) {
					System.out.println("Invalid number of elements!");
					sc.close();
					return;
				}

				crypting(args[1], args[2], sc, true);
				break;

			case "decrypt":
				if(args.length != 3) {
					System.out.println("Invalid number of elements!");
					sc.close();
					return;
				}

				crypting(args[1], args[2], sc, false);
				break;

			default:
				System.out.println("Invalid command!");
			}
		} catch (Exception e) {
			System.out.println(e.getMessage());
		}
		
		sc.close();
	}

	/**
	 * Calculates file's sha-256 of file with path given as parameter as string.
	 * If calculated sha-256 is the same as given string, program writes appropriate message.
	 * If calculated and given are different program writes out calculated sha-256.
	 * 
	 * @param path
	 * @param sc
	 */
	private static void sha256(String path, Scanner sc) {
		System.out.println("Please provide expected sha-256 digest for " + path + ":");
		System.out.print("> ");

		String expectedDigest = sc.nextLine();

		MessageDigest md = null;
		
		try {
			md = MessageDigest.getInstance("SHA-256");
		} catch (NoSuchAlgorithmException e) {
			System.out.println("Unable to digest!");
			return;
		}

		try (InputStream is = Files.newInputStream(Paths.get(path))) {
			byte[] buff = new byte[1024];
			while(true) {
				int r = is.read(buff);
				if(r < 1) {
					break;
				}
				
				md.update(buff, 0, r);
			}
		} catch(IOException ex) {
			System.out.println("Invalid paths!");
			return;
		}

		String calculatedDigest = Util.bytetohex(md.digest());

		if(expectedDigest.equals(calculatedDigest)) {
			System.out.println("Digesting completed. Digest of hw06test.bin matches expected digest.");
		} else {
			System.out.println("Digesting completed. Digest of hw06test.bin does not match the expected digest. Digest\r\n" + 
					"was: " + calculatedDigest);
		}

	}

	/**
	 * This method encrypts or decrypts file's based on boolean value it is given as argument.
	 * This method takes data from file whose path is given as input argument, processes data
	 * and writes it in file whose path is given as output argument.
	 * 
	 * @param input
	 * @param output
	 * @param sc
	 * @param encrypt
	 */
	private static void crypting(String input, String output, Scanner sc, boolean encrypt) {
		System.out.println("Please provide password as hex-encoded text (16 bytes, i.e. 32 hex-digits):");
		System.out.print("> ");

		String keyText = sc.nextLine();

		System.out.println("Please provide initialization vector as hex-encoded text (32 hex-digits):");
		System.out.print("> ");

		String ivText = sc.nextLine();

		SecretKeySpec keySpec = new SecretKeySpec(Util.hextobyte(keyText), "AES");
		AlgorithmParameterSpec paramSpec = new IvParameterSpec(Util.hextobyte(ivText));

		Cipher cipher = null;

		try {
			cipher = Cipher.getInstance("AES/CBC/PKCS5Padding");
			cipher.init(encrypt ? Cipher.ENCRYPT_MODE : Cipher.DECRYPT_MODE, keySpec, paramSpec);
		} catch (Exception e) {
			System.out.println(e.getMessage());
		}

		try (InputStream is = Files.newInputStream(Paths.get(input));
				OutputStream os = Files.newOutputStream(Paths.get(output))) {
			byte[] buff = new byte[4096];
			while(true) {
				int r = is.read(buff);
				if(r < 1) {
					break;
				}

				os.write(cipher.update(buff, 0, r));
			}

			os.write(cipher.doFinal());

			System.out.print(encrypt ? "Encryption" : "Decryption");
			System.out.println(" completed. Generated file " + input + " based on file " + output + ".");

		} catch(IOException ex) {
			System.out.println("Invalid paths!");
		} catch (IllegalBlockSizeException | BadPaddingException e) {
			System.out.println(encrypt ? "Unable to encrypt!" : "Unable to decrypt!");
		}

	}
}
