package hr.fer.zemris.java.custom.collections;

/**
 * Collection interface.
 * 
 * @author Petar Cvitanović
 *
 */
public interface Collection {
	
	/**
	 * Method isEmpty returns whether or not the collection is empty.
	 * 
	 * @return true if collection is empty, false otherwise.
	 */
	default boolean isEmpty() {
		return size() == 0;
	}
	
	/**
	 * Method size returns number of elements in collection.
	 * 
	 * @return number of elements
	 */
	int size();
	
	/**
	 * Method add appends object to the end of the collection. Duplicate
	 * objects are allowed.
	 * 
	 * @throws NullPointerException
	 * 				if object is null
	 * 
	 * @param value object to add
	 */
	void add(Object value);
	
	/**
	 * Method contains checks whether or not value is in collection.
	 * 
	 * @param value that needs to be checked.
	 * @return true if collection contains value, false otherwise.
	 */
	boolean contains(Object value);
	
	/**
	 * Removes value from collection.
	 * 
	 * @param value object that needs to be removed.
	 * @return true if value is removed, false otherwise.
	 */
	boolean remove(Object value);
	
	/**
	 * Returns all elements from collection in an array.
	 * 
	 * @throws UnsupportedOperationException
	 * 				it's not yet implemented
	 * 
	 * @return collection elements
	 */
	Object[] toArray();
	
	/**
	 * Calls processors process method on every element of
	 * the collection.
	 * 
	 * @param processor given processor
	 */
	default void forEach(Processor processor) {
		ElementsGetter getter = createElementsGetter();
		
		while(getter.hasNextElement()) {
			processor.process(getter.getNextElement());
		}
	}
	
	/**
	 * Defines a local processor class whose method process
	 * will add each item into the current collection.
	 * 
	 * @param other given collection
	 */
	default void addAll(Collection other) {
		
		class addingProcessor implements Processor {
			
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
	void clear();
	
	/**
	 * Creates instance of class that implements ElementsGetter interface.
	 * 
	 * @return ElementsGetter
	 */
	ElementsGetter createElementsGetter();
	
	/**
	 * Appends collection col's elements which tester accepts.
	 * 
	 * @param Collection
	 * @param Tester
	 */
	default void addAllSatisfying(Collection col, Tester test) {
		ElementsGetter getter = col.createElementsGetter();
		
		while(getter.hasNextElement()) {
			Object value = getter.getNextElement();
			if(test.test(value)) {
				add(value);
			}
		}
	}
}




