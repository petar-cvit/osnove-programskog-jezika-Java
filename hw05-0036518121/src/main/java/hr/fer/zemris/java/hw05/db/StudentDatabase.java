package hr.fer.zemris.java.hw05.db;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Used as database for student records. Every record is stored in a list.
 * Class has map that maps JMBAGs from records to indices from list. That way
 * records can be fetched in O(1).
 * 
 * @author Petar Cvitanović
 *
 */
public class StudentDatabase {

	/**
	 * list of records
	 */
	List<StudentRecord> records;
	
	/**
	 * map with JMBAGs and indices
	 */
	Map<String, Integer> map;
	
	/**
	 * Constructor with list strings that represent records.
	 * 
	 * @param data
	 */
	public StudentDatabase(List<String> data) {
		records = new ArrayList<StudentRecord>();
		map = new HashMap<String, Integer>();
		
		int index = 0;
		
		for(String record : data) {
			
			String[] attributes = record.split("\\s");
			int lenght = 0;
			
			for(int i = 0;i < attributes.length;i++) {
				if(!attributes[i].isBlank()) {
					lenght++;
				}
			}
			
			String[] attributesFinal = new String[lenght];
			
			for(int i = 0, k = 0;i < attributes.length;i++) {
				if(!attributes[i].isBlank()) {
					attributesFinal[k++] = attributes[i];
				}
			}
			
			if(attributesFinal.length > 5) {
				throw new IllegalArgumentException("Unable to parse data!");
			}
			
			try {
				int dummy = Integer.parseInt(attributesFinal[0]);
				dummy = Integer.parseInt(attributesFinal[attributesFinal.length - 1]);
			} catch (NumberFormatException ex) {
				throw new IllegalArgumentException("Unable to parse data!");
			}
			
			for(int i = 1;i < attributesFinal.length - 1;i++) {
				for(char c : attributesFinal[i].toCharArray()) {
					if(!Character.isAlphabetic(c) && c != '-') {
						throw new IllegalArgumentException("Unable to parse data!");
					}
				}
			}
			
			if(attributesFinal.length == 4) {
				
				records.add(new StudentRecord(attributesFinal[0],
						attributesFinal[1], attributesFinal[2], 
						Integer.parseInt(attributesFinal[3])));
			
			} else if(attributesFinal.length == 5) {
				records.add(new StudentRecord(attributesFinal[0],
						attributesFinal[1] + " " + attributesFinal[2] , attributesFinal[3], 
						Integer.parseInt(attributesFinal[4])));
			} else {
				throw new IllegalArgumentException("Unable to parse data!");
			}
			
			map.put(attributes[0], index++);
		}
	}
	
	/**
	 * Returns record that has given JMBAG.
	 * 
	 * @param jmbag
	 * @return record
	 */
	public StudentRecord forJMBAG(String jmbag) {
		if(!map.containsKey(jmbag)) {
			return null;
		}
		
		int index = map.get(jmbag);
		
		return records.get(index);
	}
	
	/**
	 * Filters all records with given filter's accepts method.
	 * 
	 * @param filter
	 * @return list of records
	 */
	public List<StudentRecord> filter(IFilter filter) {
		List<StudentRecord> acceptable = new ArrayList<StudentRecord>();
		
		for(StudentRecord record : records) {
			if(filter.accepts(record)) {
				acceptable.add(record);
			}
		}
		
		return acceptable;
	}
	
}

























