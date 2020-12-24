package hr.fer.zemris.java.custom.collections;

public class Dictionary<K, V> {
	
	private ArrayIndexedCollection<Entry<K, V>> collection;
	
	public Dictionary() {
		this.collection = new ArrayIndexedCollection<Entry<K, V>>();
	}
	
	private static class Entry<K, V> {
		
		private K key;
		
		private V value;
		
		Entry(K key, V value) {
			if(key == null) {
				throw new NullPointerException();
			}
			
			this.key = key;
			this.value = value;
		}
		
		K getKey() {
			return key;
		}
		
		V getValue() {
			return value;
		}
		
		void setValue(V value) {
			this.value = value;
		}
	}
	
	public boolean isEmpty() {
		return collection.isEmpty();
	}
	
	public int size() {
		return collection.size();
	}
	
	public void clear() {
		collection.clear();
	}
	
	public void put(K key, V value) {
		Entry<K, V> entry = iterate(key);
		
		if(entry == null) {
			collection.add(new Entry<K, V>(key, value));
		} else {
			entry.setValue(value);
		}
	}
	
	public V get(K key) {
		Entry<K, V> entry = iterate(key);
		
		if(entry == null) {
			return null;
		}
		
		return entry.getValue();
	}
	
	private Entry<K, V> iterate(K key) {
		for(int i = 0;i < collection.size();i++) {
			if(collection.get(i).getKey().equals(key)) {
				return collection.get(i);
			}
		}
		
		return null;
	}

}
















