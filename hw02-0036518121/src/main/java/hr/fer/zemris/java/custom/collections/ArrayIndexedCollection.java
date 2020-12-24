package hr.fer.zemris.java.custom.collections;

import java.util.Arrays;

/**
 * Class extends Collection class and uses its methods.
 * Collection holds elements in an object array.
 * 
 * @author Petar Cvitanović
 *
 */
public class ArrayIndexedCollection extends Collection{
	
	/**
	 * number of elements in collection
	 */
	private int size;

	/**
	 * object array that contains collections elements
	 */
	private Object[] elements;
	
	/**
	 * default capacity
	 */
	private static int CAPACITY = 16;
	
	/**
	 * Default constructor that creates an instance with capacity 16.
	 */
	public ArrayIndexedCollection() {
		this(CAPACITY);
	}
	
	/**
	 * Constructor that creates an instance with capacity
	 * of the parameter initialCapacity.
	 * 
	 * @throws IllegalArgumentException
	 * 				if initialCapacity is smaller than 1
	 * 
	 * @param initialCapacity given capacity
	 */
	public ArrayIndexedCollection(int initialCapacity) {
		if(initialCapacity < 1) {
			throw new IllegalArgumentException("Initial capacity should be greater than one.");
		}

		size = 0;
		elements = new Object[initialCapacity];
	}
	
	/**
	 * Creates an instance where elements are copied from other collection
	 * that is provided.
	 * 
	 * @throws NullPointerException
	 * 				if other collection is null
	 * 
	 * @param other collection
	 */
	public ArrayIndexedCollection(Collection other) {
		this(other, other.size());
	}
	
	/**
	 * Creates an instance where elements are copied from other collection
	 * that is provided. InitialCapacity defines size of elements array.
	 * If initialCapacity is smaller than other collections size, elements
	 * size will be other collections size.
	 * 
	 * @throws NullPointerException
	 * 				if other collection is null
	 * 
	 * @throws IllegalArgumentException
	 * 				if initialCapacity is smaller than 1
	 * 
	 * @param other given collection
	 * @param initialCapacity given capacity
	 */
	public ArrayIndexedCollection(Collection other, int initialCapacity) {
		if(other.equals(null)) {
			throw new NullPointerException();
		}
		
		if(initialCapacity < 1) {
			throw new IllegalArgumentException("Initial capacity should be greater than one.");
		}
		
		if(initialCapacity < other.size()) {
			initialCapacity = other.size();
		}
		
		elements = Arrays.copyOf(other.toArray(), initialCapacity);
		size = other.size();
	}
	
	/**
	 * @throws NullPointerException
	 * 				if object is null
	 */
	@Override
	public void add(Object value) {
		if(value.equals(null)) {
			throw new NullPointerException();
		}
		
		try {
			elements[size] = value;
			size++;
		} catch(IndexOutOfBoundsException ex) {
			elements = Arrays.copyOf(elements, elements.length * 2);
			elements[size] = value;
			size++;
		}
	}
	
	/**
	 * Return object on index that was provided.
	 * 
	 * @throws IndexOutOfBoundsException
	 * 				If index is smaller than 1 or bigger than size-1
	 * 
	 * @param index index of object
	 * @return object on index in elements
	 */
	public Object get(int index) {
		if(index < 0 || index > size-1) {
			throw new IndexOutOfBoundsException("Index out of bounds.(0, "+ (size-1) +")");
		}
		
		return elements[index];
	}

	@Override
	public void clear() {
		for(int i = 0;i < size;i++) {
			elements[i] = null;
		}
		size = 0;
	}
	
	/**
	 * Inserts given value on the position provided. All elements after
	 * position shifted one place to the right.
	 * 
	 * @throws IndexOutOfBoundsException
	 * 				if position is smaller than zero or bigger than
	 * 				collections size
	 * 
	 * @throws NullPointerException
	 * 				if value is null
	 * 
	 * @param value
	 * @param position
	 */
	public void insert(Object value, int position) {
		if(position < 0 || position > size) {
			throw new IndexOutOfBoundsException("Index out of bounds.(0, "+ (size-1) +")");
		}
		
		if(value.equals(null)) {
			throw new NullPointerException();
		}
		
		if(size == elements.length) {
			elements = Arrays.copyOf(elements, elements.length * 2);
		}
		
		for(int i = size;i > position;i--) {
			elements[i] = elements[i-1];
		}
		size++;
		elements[position] = value;
	}
	
	/**
	 * Return index of the given value in elements array.
	 * If collection doesn't contain given value, method returns -1.
	 * 
	 * @param value
	 * @return index of value
	 */
	public int indexOf(Object value) {
		if(value == null) {
			return -1;
		}
		
		for(int i = 0;i < size;i++) {
			if(elements[i].equals(value)) {
				return i;
			}
		}
		return -1;
	}
	
	/**
	 * Removes value on index in elements array.
	 * 
	 * @throws IndexOutOfBoundsException
	 * 				if index is smaller than 1 or bigger than size - 1
	 * 
	 * @param index
	 */
	public void remove(int index) {
		if(index < 0 || index > size - 1) {
			throw new IndexOutOfBoundsException("Index out of bounds.(0, "+ (size-1) +")");
		}

		size--;
		
		for(int i = index;i < size;i++) {
			elements[i] = elements[i + 1];
		}
		
		elements[size] = null;
	}
	
	/**
	 * Removes value from collection.
	 * 
	 * @param value that needs to be removed
	 * @return true if value is removed, false otherwise
	 */
	@Override
	public boolean remove(Object value) {
		int index = indexOf(value);
		if(index == -1) {
			return false;
		}
		
		remove(index);
		return true;
	}
	
	/**
	 * Method size returns number of elements in collection.
	 * 
	 * @return number of elements
	 */
	@Override
	public int size() {
		return size;
	}
	
	/**
	 * Returns all elements from collection in an array.
	 * 
	 * @return collection elements
	 */
	@Override
	public Object[] toArray() {
		Object[] out = new Object[size];
		int j = 0;
		for(int i = 0;i < size;i++) {
			if(elements[i] != (null))
				out[j++] = elements[i];
		}
		
		return out;
	}
	
	@Override
	public boolean contains(Object value) {
		for(int i = 0;i < size;i++) {
			if(elements[i].equals(value)) {
				return true;
			}
		}
		return false;
	}
	
	@Override
	public void forEach(Processor processor) {
		for(int i = 0;i < size;i++) {
			processor.process(elements[i]);
		}
	}
	
}













