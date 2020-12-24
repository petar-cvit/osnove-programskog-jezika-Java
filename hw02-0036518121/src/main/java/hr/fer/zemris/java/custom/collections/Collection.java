package hr.fer.zemris.java.custom.collections;

/**
 * Collection class acts as an abstract class to subclasses.
 * 
 * @author Petar Cvitanović
 *
 */
public class Collection {

	/**
	 * Default constructor.
	 */
	protected Collection() {
		
	}
	
	/**
	 * Method isEmpty returns whether or not the collection is empty.
	 * 
	 * @return true if collection is empty, false otherwise.
	 */
	public boolean isEmpty() {
		return size() == 0;
	}
	
	/**
	 * Method size returns number of elements in collection.
	 * 
	 * @return number of elements
	 */
	public int size() {
		return 0;
	}
	
	/**
	 * Method add appends object to the end of the collection. Duplicate
	 * objects are allowed.
	 * 
	 * @throws NullPointerException
	 * 				if object is null
	 * 
	 * @param value object to add
	 */
	public void add(Object value) {
		
	}
	
	/**
	 * Method contains checks whether or not value is in collection.
	 * 
	 * @param value that needs to be checked.
	 * @return true if collection contains value, false otherwise.
	 */
	public boolean contains(Object value) {
		return false;
	}
	
	/**
	 * Removes value from collection.
	 * 
	 * @param value object that needs to be removed.
	 * @return true if value is removed, false otherwise.
	 */
	public boolean remove(Object value) {
		return false;
	}
	
	/**
	 * Returns all elements from collection in an array.
	 * 
	 * @throws UnsupportedOperationException
	 * 				it's not yet implemented
	 * 
	 * @return collection elements
	 */
	public Object[] toArray() {
		throw new UnsupportedOperationException();
	}
	
	/**
	 * Calls processors process method on every element of
	 * the collection.
	 * 
	 * @param processor given processor
	 */
	public void forEach(Processor processor) {
		
	}
	
	/**
	 * Defines a local processor class whose method process
	 * will add each item into the current collection.
	 * 
	 * @param other given collection
	 */
	public void addAll(Collection other) {
		
		class addingProcessor extends Processor {
			
			@Override
			public void process(Object value) {
				add(value);
			}
		}
		
		other.forEach(new addingProcessor());
	}
	
	/**
	 * Removes all elements from this collection
	 */
	public void clear() {
		
	}
}




