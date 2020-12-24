package hr.fer.zemris.java.custom.collections;

import java.util.ConcurrentModificationException;
import java.util.Iterator;

public class SimpleHashtable<K, V> implements Iterable<SimpleHashtable.TableEntry<K, V>>{
	
	private TableEntry<K, V>[] table;
	private int size;
	private int modificationCount;
	
	public SimpleHashtable() {
		this(16);
	}
	
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
	
	private void overfill() {
		if(((double)size / (double)table.length) >= 0.75) {
			makeNewTable();
		}
	}
	
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
		
	private int getNextBigger(int n) {
		int out = 1;
		while(true) {
			if(out >= n) {
				return out;
			}
			
			out *= 2;
		}
	}
	
	public static class TableEntry<K, V> {
		
		private K key;
		
		private V value;
		
		private TableEntry<K, V> next;
		
		public TableEntry(K key, V value, TableEntry<K, V> next) {
			this.key = key;
			this.value = value;
			this.next = next;
		}
		
		public K getKey() {
			return key;
		}
		
		public V getValue() {
			return value;
		}
		
		public void setValue(V value) {
			this.value = value;
		}
		
		public String toString() {
			return key.toString() + "=" + value.toString();
		}
	}
	
	private class IteratorImpl implements Iterator<SimpleHashtable.TableEntry<K, V>> {
		
		private TableEntry<K, V> current;
		
		private int currentIndex;
		
		private int savedModificationCount;
		
		public IteratorImpl() {
			currentIndex = 0;
			current = nextSlot();
			savedModificationCount = modificationCount; 
		}
		
		@Override
		public boolean hasNext() {
			return current != null;
		}

		@Override
		public TableEntry<K, V> next() {
			if(modificationCount != savedModificationCount) {
				throw new ConcurrentModificationException();
			}
			
			TableEntry<K, V> out = current;
			
			if(current.next == null) {
				current = nextSlot();
			} else {
				current = current.next;
			}
			
			return out;
		}
		
		public void remove() {
			
		}
		
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
	
	private int getHash(K key) {
		return Math.abs(key.hashCode()) % table.length;
	}
	
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
					
					break;
				}
				current = current.next;
			}
		}
		
	}
	
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
	
	public int size() {
		return size;
	}
	
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
			return;
			
		} else {
			while(current != null) {
				if(current.next == null) {
					break;
				}
				if(current.next.key.equals(key)) {
					current.next = current.next.next;
					size--;
					break;
				}
				
				current = current.next;
			}
		}
		
	}
	
	public boolean isEmpty() {
		return size == 0;
	}
	
	public void clear() {
		for(int i = 0;i < table.length;i++) {
			table[i] = null;
		}
		
		modificationCount++;
	}
	
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
	
	public static void main(String[] args) {
		// create collection:
		SimpleHashtable<String, Integer> examMarks = new SimpleHashtable<>(2);
		System.out.println(examMarks.size());
		
		// fill data:
		examMarks.put("Ivana", 2);
		System.out.println("cap = "+ examMarks.table.length);
		examMarks.put("Ante", 2);
		System.out.println("cap = "+ examMarks.table.length);
		examMarks.put("Jasna", 2);
		System.out.println("cap = "+ examMarks.table.length);
		examMarks.put("Kristina", 5);
		System.out.println("cap = "+ examMarks.table.length);
		examMarks.put("Ivana", 5); // overwrites old grade for Ivana
		examMarks.put("Cilek", 6);
		System.out.println("cap = "+ examMarks.table.length);
		examMarks.put("Ivanam", 2);
		System.out.println("cap = "+ examMarks.table.length);
		//examMarks.put("jajceka", 2);
		//System.out.println("cap = "+ examMarks.table.length);
		examMarks.put("Ivanal", 2);
		System.out.println("cap = "+ examMarks.table.length);
		
		// query collection:
		Integer kristinaGrade = examMarks.get(null);
		System.out.println("Kristina's exam grade is: " + kristinaGrade); // writes: 5
		// What is collection's size? Must be four!
		System.out.println("Number of stored pairs: " + examMarks.size()); // writes: 4
		
		System.out.println(examMarks.toString());
		System.out.println(examMarks.size());
		
		//examMarks.remove("Ante");
		//System.out.println(examMarks.toString());
		
		System.out.println("Ivana " + examMarks.containsKey("Ivana"));
		System.out.println("Jasna " + examMarks.containsKey("Jasna"));
		System.out.println("Kristina " + examMarks.containsKey("Kristina"));
		System.out.println("Marko " + examMarks.containsKey("marko"));
		System.out.println("n " + examMarks.containsKey(null));
		System.out.println("2 " + examMarks.containsKey(2));
		
		examMarks.remove("Kristina");
		System.out.println(examMarks.toString());
		System.out.println(examMarks.size());
		
		System.out.println("Ivana " + examMarks.containsKey("Ivana"));
		System.out.println("Jasna " + examMarks.containsKey("Jasna"));
		System.out.println("Kristina " + examMarks.containsKey("Kristina"));
		System.out.println("Marko " + examMarks.containsKey("marko"));
		System.out.println("n " + examMarks.containsKey(null));
		System.out.println("2 " + examMarks.containsKey(2));
		
		examMarks.remove(3);
		System.out.println(examMarks.toString());
		System.out.println(examMarks.size());
		
		examMarks.remove(null);
		System.out.println(examMarks.toString());
		System.out.println(examMarks.size());
		
		System.out.println(examMarks.isEmpty());

		System.out.println(examMarks.get("Ante"));
		System.out.println(examMarks.get("Ivana"));
		System.out.println(examMarks.get("Jasna"));
		System.out.println(examMarks.get("Kristina"));
		System.out.println(examMarks.get(3));
		System.out.println(examMarks.get(null));
		
		System.out.println(examMarks);
		System.out.println(examMarks.table.length);
		
		//examMarks.clear();
		
		System.out.println(examMarks);
		System.out.println(examMarks.table.length);
		
		
		System.out.println();
		
		Iterator<SimpleHashtable.TableEntry<String,Integer>> iter = examMarks.iterator();
		for(SimpleHashtable.TableEntry<String,Integer> pair1 : examMarks) {
			for(SimpleHashtable.TableEntry<String,Integer> pair2 : examMarks) {
				System.out.printf(
						"(%s => %d) - (%s => %d)%n",
						pair1.getKey(), pair1.getValue(),
						pair2.getKey(), pair2.getValue()
						);
			}
		}
 	}

	
}




















