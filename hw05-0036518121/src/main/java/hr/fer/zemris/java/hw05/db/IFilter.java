package hr.fer.zemris.java.hw05.db;

/**
 * Interface with one method to determine whether or not some record is acceptable.
 * 
 * @author Petar Cvitanović
 *
 */
public interface IFilter {
	
	/**
	 * Returns true if given record is acceptable, false otherwise.
	 * 
	 * @param record
	 * @return boolean
	 */
	public boolean accepts(StudentRecord record);

}
