package hr.fer.zemris.java.custom.collections;

/**
 * Interface with one method. It is used to test whether an object is
 * acceptable or not.
 * 
 * @author Petar Cvitanović
 *
 */
public interface Tester<T> {
	
	/**
	 * Return true if object is acceptable, false otherwise.
	 * 
	 * @param Object
	 * @return boolean
	 */
	boolean test(T obj);
}
