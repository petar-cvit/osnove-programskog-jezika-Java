package hr.fer.zemris.java.custom.collections;

/**
 * Implementation of stack using ArrayIndexedCollection.
 * 
 * @author Petar Cvitanović
 *
 */
public class ObjectStack {
	
	/**
	 * collection of elements
	 */
	private ArrayIndexedCollection array;
	
	/**
	 * Creates an empty ArrayIndexedCollection of size 16.
	 */
	public ObjectStack() {
		 array = new ArrayIndexedCollection();
	}
	
	/**
	 * Method isEmpty returns whether or not the stack is empty.
	 * 
	 * @return true if collection is empty, false otherwise.
	 */
	public boolean isEmpty() {
		return array.isEmpty();
	}
	
	/**
	 * Method size returns number of elements in collection.
	 * 
	 * @return number of elements
	 */
	public int size() {
		return array.size();
	}
	
	/**
	 * Method add puts object on top of the stack. Duplicate
	 * objects are allowed.
	 * 
	 * @throws NullPointerException
	 * 				if object is null
	 * 
	 * @param value
	 */
	public void push(Object value) {
		array.add(value);
	}
	
	/**
	 * Returns last added object and removes it from stack.
	 * 
	 * @return last added object
	 */
	public Object pop() {
		Object value = peek();
		
		array.remove(array.size() - 1);
		return value;
	}
	
	/**
	 * Returns last added object.
	 * 
	 * @return last added object
	 */
	public Object peek() {
		if(size() == 0) {
			throw new EmptyStackException("Stack is already empty!");
		}
		
		return array.get(size() - 1);
	}
	
	/**
	 * Removes all elements from this collection
	 */
	public void clear() {
		array.clear();
	}
}












