package hr.fer.zemris.java.hw05.db;

import java.util.ArrayList;
import java.util.List;

/**
 * Used to format records to GUI-like output on console.
 * 
 * @author Petar Cvitanović
 *
 */
public class RecordFormatter {
	
	/**
	 * Formats list of records for output on console.
	 * 
	 * @param records
	 * @return list of strings
	 */
	public static List<String> format(List<StudentRecord> records) {
		List<String> out = new ArrayList<String>();
		
		int longestJmbag = 0;
		int longestName = 0;
		int longestLastName = 0;
		
		for(StudentRecord record : records) {
			longestJmbag = Math.max(record.getJMBAG().length(), longestJmbag);
			longestName = Math.max(record.getFirstName().length(), longestName);
			longestLastName = Math.max(record.getLastName().length(), longestLastName);
		}
		
		out.add(firstLine(longestJmbag, longestName, longestLastName));
		
		String current;
		
		for(StudentRecord record : records) {
			current = "| ";
			
			current = current.concat(record.getJMBAG());
			for(int i = 0;i < longestJmbag - record.getJMBAG().length();i++) {
				current = current.concat(" ");
			}
			current = current.concat(" | ");
			
			current = current.concat(record.getLastName());
			for(int i = 0;i < longestLastName - record.getLastName().length();i++) {
				current = current.concat(" ");
			}
			current = current.concat(" | ");
			
			current = current.concat(record.getFirstName());
			for(int i = 0;i < longestName - record.getFirstName().length();i++) {
				current = current.concat(" ");
			}
			current = current.concat(" | ");
			
			current = current.concat(Integer.valueOf(record.getFinalGrade()).toString());
			for(int i = 0;i < longestJmbag - record.getJMBAG().length();i++) {
				current = current.concat(" ");
			}
			current = current.concat(" |");
			
			out.add(current);
		}
		
		out.add(firstLine(longestJmbag, longestName, longestLastName));
		
		if(out.size() == 2) {
			out.clear();
			out.add("Records selected: 0");
		} else {
			out.add("Records selected: "+ (out.size() - 2));
		}
		return out;		
	}
	
	/**
	 * Generates first and last line of output.
	 * 
	 * @param longestJmbag
	 * @param longestName
	 * @param longestLastName
	 * @return line in string format
	 */
	private static String firstLine(int longestJmbag, int longestName, int longestLastName) {
		String current = "+=";
		for(int i = 0;i < longestJmbag;i++) {
			current = current.concat("=");
		}
		
		current = current.concat("=+=");
		for(int i = 0;i < longestLastName;i++) {
			current = current.concat("=");
		}
		
		current = current.concat("=+=");
		for(int i = 0;i < longestName;i++) {
			current = current.concat("=");
		}
		
		current = current.concat("=+===+");
		
		return current;
	}

}
