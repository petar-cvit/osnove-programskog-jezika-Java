package hr.fer.zemris.java.custom.collections;

/**
 * Processor class with process method.
 * 
 * @author Petar Cvitanović
 *
 */
public interface Processor<T> {
	
	/**
	 * Processes value.
	 * 
	 * @param value
	 */
	void process(T value);
}
