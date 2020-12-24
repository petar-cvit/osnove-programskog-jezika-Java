package hr.fer.zemris.java.custom.collections;

/**
 * Class List extends Collection class.
 * 
 * @author Petar Cvitanović
 *
 */
public interface List extends Collection{

	/**
	 * Returns object from collection on index.
	 * 
	 * @param index of object
	 * @return Object
	 */
	Object get(int index);
	
	/**
	 * Inserts Object to position in collection.
	 * 
	 * @param value
	 * @param position
	 */
	void insert(Object value, int position);
	
	/**
	 * Returns index of object. If collection doesn't contain object returns -1.
	 * 
	 * @param value
	 * @return index
	 */
	int indexOf(Object value);
	
	/**
	 * Removes object from collection.
	 * 
	 * @param index
	 */
	void remove(int index);
}
