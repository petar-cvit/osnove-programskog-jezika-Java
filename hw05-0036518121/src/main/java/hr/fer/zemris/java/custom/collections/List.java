package hr.fer.zemris.java.custom.collections;

/**
 * Class List extends Collection class with parameter T.
 * 
 * @author Petar Cvitanović
 * @param <T>
 *
 */
public interface List<T> extends Collection<T>{

	/**
	 * Returns element from collection on index.
	 * 
	 * @param index of element
	 * @return element
	 */
	T get(int index);
	
	/**
	 * Inserts element to position in collection.
	 * 
	 * @param value
	 * @param position
	 */
	void insert(T value, int position);
	
	/**
	 * Returns index of element. If collection doesn't contain element returns -1.
	 * 
	 * @param value
	 * @return index
	 */
	int indexOf(Object value);
	
	/**
	 * Removes element from collection.
	 * 
	 * @param index
	 */
	void remove(int index);
}
