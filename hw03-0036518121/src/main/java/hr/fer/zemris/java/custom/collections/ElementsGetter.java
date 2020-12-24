package hr.fer.zemris.java.custom.collections;

/**
 * ElementsGetter interface retrieves next element from collection.
 * 
 * @author Petar Cvitanović
 *
 */
public interface ElementsGetter {

	/**
	 * Returns whether or not collection has more elements.
	 * 
	 * @return boolean
	 */
	boolean hasNextElement();

	/**
	 * Returns next element from collection..
	 * 
	 * @return Object next element from collection.
	 */
	Object getNextElement();
	
	/**
	 * Calls Processor p's process method on every element left in collection.
	 * 
	 * @param Processor p
	 */
	void processRemaining(Processor p);
}
