package hr.fer.zemris.java.hw05.db;

/**
 * Returns attributes from records.
 * 
 * @author Petar Cvitanović
 *
 */
public interface IFieldValueGetter {
	
	/**
	 * Returns attribute.
	 * 
	 * @param record
	 * @return attribute.
	 */
	public String get(StudentRecord record);

}
