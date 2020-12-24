package hr.fer.zemris.java.custom.collections;

import java.util.Arrays;
import java.util.ConcurrentModificationException;
import java.util.NoSuchElementException;

/**
 * Class extends Collection class and uses its methods. it is parametrised with one parameter.
 * Collection holds elements in an array.
 * 
 * @author Petar Cvitanović
 * @param <T>
 *
 */
public class ArrayIndexedCollection<T> implements List<T>{
	
	/**
	 * number of elements in collection
	 */
	private int size;

	/**
	 * object array that contains collections elements
	 */
	private T[] elements;
	
	/**
	 * counts modifications on collection
	 */
	private long modificationCount;
	
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
	@SuppressWarnings("unchecked")
	public ArrayIndexedCollection(int initialCapacity) {
		if(initialCapacity < 1) {
			throw new IllegalArgumentException("Initial capacity should be greater than one.");
		}

		size = 0;
		elements = (T[]) new Object[initialCapacity];
		modificationCount = 0;
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
	public ArrayIndexedCollection(Collection<T> other) {
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
	public ArrayIndexedCollection(Collection<T> other, int initialCapacity) {
		if(other.equals(null)) {
			throw new NullPointerException();
		}
		
		if(initialCapacity < 1) {
			throw new IllegalArgumentException("Initial capacity should be greater than one.");
		}
		
		if(initialCapacity < other.size()) {
			initialCapacity = other.size();
		}
		
		elements = (T[]) Arrays.copyOf(other.toArray(), initialCapacity);
		size = other.size();
		modificationCount = 0;
	}

	/**
	 * ArrayGetter class implements ElementsGetter interface.
	 * Used for getting next element from collection.
	 * 
	 * @author Petar Cvitanović
	 *
	 */
	private static class ArrayGetter<T> implements ElementsGetter<T>{
		
		/**
		 * index of current element from collection
		 */
		int current;
		
		/**
		 * modificationCount of collection when ArrayGetter was initialized
		 */
		long savedModificationCount;
		
		/**
		 * reference to collection
		 */
		ArrayIndexedCollection<T> collection;
		
		/**
		 * Constructor with collection reference.
		 * 
		 * @param collection
		 */
		public ArrayGetter(ArrayIndexedCollection<T> collection) {
			savedModificationCount = collection.modificationCount;
			current = 0;
			this.collection = collection;
		}
		
		/**
		 * @throws ConcurrentModificationException
		 * 				if element is added or removed from collection
		 */
		@Override
		public boolean hasNextElement() {
			if (savedModificationCount != collection.modificationCount) {
				throw new ConcurrentModificationException();
			}
			return current < collection.size();
		}

		/**
		 * @throws ConcurrentModificationException
		 * 				if element is added or removed from collection
		 */
		@Override
		public T getNextElement() {
			if (savedModificationCount != collection.modificationCount) {
				throw new ConcurrentModificationException();
			}
			if (!hasNextElement()) {
				throw new NoSuchElementException();
			}
			return collection.get(current++);
		}

		@Override
		public void processRemaining(Processor<T> p) {
			for(int i = current;i < collection.size;i++) {
				p.process(collection.get(i));
			}
		}
	}
	
	@Override
	public ArrayGetter<T> createElementsGetter() {
		return new ArrayGetter<T>(this);
	}
	
	/**
	 * @throws NullPointerException
	 * 				if object is null
	 */
	@Override
	public void add(T value) {
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
		
		modificationCount++;
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
	@Override
	public T get(int index) {
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
		modificationCount++;
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
	@Override
	public void insert(T value, int position) {
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
		
		modificationCount++;
	}
	
	/**
	 * Return index of the given value in elements array.
	 * If collection doesn't contain given value, method returns -1.
	 * 
	 * @param value
	 * @return index of value
	 */
	@Override
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
	@Override
	public void remove(int index) {
		if(index < 0 || index > size - 1) {
			throw new IndexOutOfBoundsException("Index out of bounds.(0, "+ (size-1) +")");
		}

		size--;
		
		for(int i = index;i < size;i++) {
			elements[i] = elements[i + 1];
		}
		
		elements[size] = null;
		
		modificationCount++;
	}
	
	/**
	 * Removes value from collection.
	 * 
	 * @param value that needs to be removed
	 * @return true if value is removed, false otherwise
	 */
	@Override
	public boolean remove(T value) {
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
	@SuppressWarnings("unchecked")
	@Override
	public T[] toArray() {
		T[] out = (T[]) new Object[size];
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

	
}













