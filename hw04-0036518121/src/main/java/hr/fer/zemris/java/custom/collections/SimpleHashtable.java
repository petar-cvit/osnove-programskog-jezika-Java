package hr.fer.zemris.java.custom.collections;

import java.util.ConcurrentModificationException;
import java.util.Iterator;

/**
 * SimpleHashtable class is used as a map. It stores key-value pairs. Keys can't be null object while values can.
 * Class is iterable and has factory methods to create new iterators. Every entry's key is hashed and entry is stored
 * in a slot of an array depending on hashing result. Collisions are handled with chaining.
 * 
 * 
 * @author Petar Cvitanović
 *
 * @param <K>
 * @param <V>
 */
public class SimpleHashtable<K, V> implements Iterable<SimpleHashtable.TableEntry<K, V>>{
	
	/**
	 * array of entries
	 */
	private TableEntry<K, V>[] table;
	
	/**
	 * number of entries
	 */
	private int size;
	
	/**
	 * number of modifications on a hashtable
	 */
	private int modificationCount;
	
	/**
	 * maximum ratio of values ​​between number of entries and slots
	 */
	private final static double MAXOCCUPANCY = 0.75;
	
	/**
	 * Default constructor. Creates array of capacity 16.
	 */
	public SimpleHashtable() {
		this(16);
	}
	
	/**
	 * Constructor that creates array. This array's capacity is first number that is the potency of number 2.
	 * 
	 * @throws IllegalArgumentException
	 * 				if given capacity is less than one
	 * 
	 * @param capacity
	 */
	@SuppressWarnings("unchecked")
	public SimpleHashtable(int capacity) {
		if(capacity < 1) {
			throw new IllegalArgumentException("Capacity has to be bigger than zero!");
		}
		
		capacity = getNextBigger(capacity);
		table = (TableEntry<K, V>[]) new TableEntry[capacity];
		size = 0;
		modificationCount = 0;
	}
	
	/**
	 * Private method that checks whether or not table needs resizing. If not method doesn't do anything.
	 */
	private void overfill() {
		if(((double)size / (double)table.length) >= MAXOCCUPANCY) {
			makeNewTable();
		}
	}
	
	/**
	 * Private method that resizes table to be twice as long.
	 */
	@SuppressWarnings("unchecked")
	private void makeNewTable() {
		size = 0;
		
		TableEntry<K, V>[] oldTable = table;
		table = (TableEntry<K, V>[]) new TableEntry[table.length * 2];
		
		for(int i = 0;i < oldTable.length;i++) {
			TableEntry<K, V> current = oldTable[i];
			
			while(current != null) {
				put(current.key, current.value);
				current = current.next;
			}
		}
	}
		
	/**
	 * Returns first number that is the first number being the potency of the number 2.
	 * 
	 * @param n
	 * @return first number being the potency of the number 2
	 */
	private int getNextBigger(int n) {
		int out = 1;
		while(true) {
			if(out >= n) {
				return out;
			}
			
			out *= 2;
		}
	}
	
	/**
	 * TableEntry class represents one entry in a hashtable.
	 * 
	 * @author Petar Cvitanović
	 *
	 * @param <K>
	 * @param <V>
	 */
	public static class TableEntry<K, V> {
		
		/**
		 * entry's key
		 */
		private K key;
		
		/**
		 * entry's value
		 */
		private V value;
		
		/**
		 * next entry in the same slot
		 */
		private TableEntry<K, V> next;
		
		/**
		 * Constructor with key, value and next entry.
		 * 
		 * @throws NullPointerException
		 * 				if key is a null object
		 * 
		 * @param key
		 * @param value
		 * @param next
		 */
		public TableEntry(K key, V value, TableEntry<K, V> next) {
			if(key == null) {
				throw new NullPointerException();
			}
			
			this.key = key;
			this.value = value;
			this.next = next;
		}
		
		/**
		 * Key getter.
		 * 
		 * @return key
		 */
		public K getKey() {
			return key;
		}
		
		/**
		 * Value getter.
		 * 
		 * @return value
		 */
		public V getValue() {
			return value;
		}
		
		/**
		 * Value setter.
		 * 
		 * @param value
		 */
		public void setValue(V value) {
			this.value = value;
		}
		
		/**
		 * Returns entry in a string format.
		 */
		public String toString() {
			if(value == null) {
				return key.toString() + "=null";
			}
			return key.toString() + "=" + value.toString();
		}
	}
	
	/**
	 * Iterator implementation.
	 * 
	 * @author Petar Cvitanović
	 *
	 */
	private class IteratorImpl implements Iterator<SimpleHashtable.TableEntry<K, V>> {
		
		/**
		 * next entry to return
		 */
		private TableEntry<K, V> current;
		
		/**
		 * last returned entry
		 */
		private TableEntry<K, V> prev;
		
		/**
		 * index of a current slot
		 */
		private int currentIndex;
		
		/**
		 * hashtable modification count when the iterator was constructed
		 */
		private int savedModificationCount;
		
		/**
		 * Default constructor
		 */
		public IteratorImpl() {
			currentIndex = 0;
			current = nextSlot();
			prev = null;
			savedModificationCount = modificationCount; 
		}
		
		@Override
		public boolean hasNext() {
			if(modificationCount != savedModificationCount) {
				throw new ConcurrentModificationException();
			}
			
			return current != null;
		}

		/**
		 * @throws ConcurrentModificationException
		 * 				if table has been changed
		 */
		@Override
		public TableEntry<K, V> next() {
			if(modificationCount != savedModificationCount) {
				throw new ConcurrentModificationException();
			}
			
			prev = current;
			
			if(current.next == null) {
				current = nextSlot();
			} else {
				current = current.next;
			}
			
			return prev;
		}
		
		/**
		 * Removes last returned element.
		 * 
		 * @throws IllegalStateException
		 * 				if method is called multiple times after one call of a next() method
		 * 
		 * @throws ConcurrentModificationException
		 * 				if table has been changed
		 */
		public void remove() {
			if(modificationCount != savedModificationCount) {
				throw new ConcurrentModificationException();
			}
			
			if(prev == null) {
				throw new IllegalStateException();
			}
			
			savedModificationCount++;
			SimpleHashtable.this.remove(prev.key);
			
			prev = null;
		}
		
		/**
		 * Private method that returns first entry in next not null slot.
		 * 
		 * @return entry
		 */
		private TableEntry<K, V> nextSlot() {
			for(int i = currentIndex + 1; i < table.length;i++) {
				if(table[i] != null) {
					currentIndex = i;
					return table[i];
				}
			}
			
			return null;
		}
		
	}
	
	/**
	 * Private method that calculates index of entry in table depending on key value.
	 * 
	 * @param key
	 * @return index in table
	 */
	private int getHash(K key) {
		return Math.abs(key.hashCode()) % table.length;
	}
	
	/**
	 * Makes a new entry in table. If given key already exists, method rewrites its value with given value. 
	 * 
	 * @param key
	 * @param value
	 */
	public void put(K key, V value) {
		if(key == null) {
			throw new NullPointerException();
		}
		
		TableEntry<K, V> current = table[getHash(key)];
		
		if(current == null) {
			table[getHash(key)] = new TableEntry<K, V>(key, value, null);
			size++;
			modificationCount++;
			overfill();
			
		} else {
			while(true) {
				if(current.key.equals(key)) {
					current.value = value;
					break;
				} else if(current.next == null){
					current.next = new TableEntry<K, V>(key, value, null);
					size++;
					modificationCount++;
					overfill();
					
					break;
				}
				current = current.next;
			}
		}
		
	}
	
	/**
	 * Returns value of entry with given key.
	 * 
	 * @param key
	 * @return value
	 */
	public V get(Object key) {
		if(key == null) {
			return null;
		}
		
		int index = Math.abs(key.hashCode()) % table.length;
		
		TableEntry<K, V> current = table[index];
		
		while(current != null) {
			if(current.key.equals(key)) {
				return current.value;
			}
			
			current = current.next;
		}
		
		return null;
	}
	
	/**
	 * Returns number of entries.
	 * 
	 * @return size
	 */
	public int size() {
		return size;
	}
	
	/**
	 * Checks whether or not table contains entry with given key.
	 * 
	 * @param key
	 * @return boolean
	 */
	public boolean containsKey(Object key) {
		if(key == null) {
			return false;
		}
		
		int index = Math.abs(key.hashCode()) % table.length;
		
		TableEntry<K, V> current = table[index];
		
		while(current != null) {
			if(current.key.equals(key)) {
				return true;
			}
			
			current = current.next;
		}
		
		return false;
	}
	
	/**
	 * Checks whether or not table contains entry with given value.
	 * 
	 * @param value
	 * @return boolean
	 */
	public boolean containsValue(Object value) {
		for(int i = 0;i < table.length;i++) {
			TableEntry<K, V> current = table[i];
			
			while(current != null) {
				if(current.value.equals(value)) {
					return true;
				}
				
				current = current.next;
			}
		}
		
		return false;
	}

	/**
	 * Removes entry with given key.
	 * 
	 * @param key
	 */
	public void remove(Object key) {
		if(key == null) {
			return;
		}
		
		int index = Math.abs(key.hashCode()) % table.length;
		
		TableEntry<K, V> current = table[index];
		
		if(current == null) {
			return;
		}
		
		if(current.key.equals(key)) {
			table[index] = current.next;
			size--;
			modificationCount++;
			return;
			
		} else {
			while(current != null) {
				if(current.next == null) {
					break;
				}
				if(current.next.key.equals(key)) {
					current.next = current.next.next;
					size--;
					modificationCount++;
					break;
				}
				
				current = current.next;
			}
		}
		
	}
	
	/**
	 * Returns whether or not table is empty.
	 * 
	 * @return
	 */
	public boolean isEmpty() {
		return size == 0;
	}
	
	/**
	 * Removes all entries from table.
	 */
	public void clear() {
		for(int i = 0;i < table.length;i++) {
			table[i] = null;
		}
		
		modificationCount++;
	}
	
	/**
	 * Returns string representation of table.
	 */
	public String toString() {
		String out = "[";
		
		for(int i = 0;i < table.length;i++) {
			TableEntry<K, V> current = table[i];
			
			while(current != null) {
				if(!out.equals("[")) {
					out = out.concat(", ");
				}
				
				out = out.concat(current.toString());
				current = current.next;
			}
		}
		
		return out.concat("]");
	}
	
	@Override
	public Iterator<TableEntry<K, V>> iterator() {
		return new IteratorImpl();
	}

}
