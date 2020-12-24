package hr.fer.zemris.java.custom.collections;

/**
 * Dictionary class represents a simple map implementation. It is parametrised with two types.
 * Class has nested Entry class which represents one dictionary pair(key, value).
 * Keys can't be duplicates while values can. Dictionary is implemented as an adapter around 
 * ArrayIndexedCollection.
 * 
 * @author Petar Cvitanović
 *
 * @param <K>
 * @param <V>
 */
public class Dictionary<K, V> {
	
	/**
	 * Collection of entries.
	 */
	private ArrayIndexedCollection<Entry<K, V>> collection;
	
	/**
	 * Default constructor.
	 */
	public Dictionary() {
		this.collection = new ArrayIndexedCollection<Entry<K, V>>();
	}
	
	/**
	 * Entry class that represents one dictionary pair. Parametrised with two types.
	 * 
	 * @author Petar Cvitanović
	 *
	 * @param <K>
	 * @param <V>
	 */
	private static class Entry<K, V> {
		
		/**
		 * entry key
		 */
		private K key;
		
		/**
		 * entry Value
		 */
		private V value;
		
		/**
		 * Constructor with key and value.
		 * 
		 * @param key
		 * @param value
		 */
		Entry(K key, V value) {
			if(key == null) {
				throw new NullPointerException();
			}
			
			this.key = key;
			this.value = value;
		}
		
		/**
		 * Key getter.
		 * 
		 * @return key
		 */
		K getKey() {
			return key;
		}
		
		/**
		 * Value getter
		 * 
		 * @return value
		 */
		V getValue() {
			return value;
		}
		
		/**
		 * Value setter
		 * 
		 * @param value
		 */
		void setValue(V value) {
			this.value = value;
		}
	}
	
	/**
	 * Returns whether or not dictionary is empty. 
	 * 
	 * @return boolean
	 */
	public boolean isEmpty() {
		return collection.isEmpty();
	}
	
	/**
	 * Returns number of entries.
	 * 
	 * @return number of entries
	 */
	public int size() {
		return collection.size();
	}
	
	/**
	 * Deletes all entries from dictionary.
	 */
	public void clear() {
		collection.clear();
	}
	
	/**
	 * Makes new entry in the dictionary. If new key already exists rewrites entry's value with new value.
	 * 
	 * @param key
	 * @param value
	 */
	public void put(K key, V value) {
		Entry<K, V> entry = iterate(key);
		
		if(entry == null) {
			collection.add(new Entry<K, V>(key, value));
		} else {
			entry.setValue(value);
		}
	}
	
	/**
	 * Returns value paired with given key.
	 * 
	 * @param key
	 * @return value
	 */
	public V get(Object key) {
		Entry<K, V> entry = iterate(key);
		
		if(entry == null) {
			return null;
		}
		
		return entry.getValue();
	}
	
	/**
	 * Goes through entry collection and returns entry with the same key.
	 * 
	 * @param key
	 * @return
	 */
	private Entry<K, V> iterate(Object key) {
		for(int i = 0;i < collection.size();i++) {
			if(collection.get(i).getKey().equals(key)) {
				return collection.get(i);
			}
		}
		
		return null;
	}

}
















