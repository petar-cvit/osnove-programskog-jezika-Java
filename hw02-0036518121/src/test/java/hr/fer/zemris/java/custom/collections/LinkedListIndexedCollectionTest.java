package hr.fer.zemris.java.custom.collections;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;

class LinkedListIndexedCollectionTest {

	LinkedListIndexedCollection list = new LinkedListIndexedCollection();
	
	@Test
	public void isEmpty() {
		assertTrue(list.isEmpty());
		
		list.add(2);
		assertTrue(!list.isEmpty());
	}
	
	@Test
	public void constructorWithCollection() {
		list.add(1);
		list.add(2);
		list.add(3);
		
		ArrayIndexedCollection newCol = new ArrayIndexedCollection(list);
		
		assertEquals(1, newCol.toArray()[0]);
		assertEquals(2, newCol.toArray()[1]);
		assertEquals(3, newCol.toArray()[2]);
	}
	
	@Test
	public void add() {
		list.add("Braco");
		list.add(2);
		
		assertTrue(list.toArray()[0].equals("Braco"));
		assertEquals(2, list.toArray()[1]);
	}
	
	@Test
	public void addException() {
		assertThrows(NullPointerException.class, () -> {
		     list.add(null);;
		  });
	}

	@Test
	public void get() {
		list.add(1);
		list.add("Braco");
		list.add(3);
		
		assertEquals(1, list.get(0));
		assertTrue(list.get(1).equals("Braco"));
		assertEquals(3, list.get(2));
	}
	
	@Test
	public void getException() {
		list.add(3);
		list.add("škola");
		
		assertThrows(IndexOutOfBoundsException.class, () -> {
		     list.get(2);
		  });
		assertThrows(IndexOutOfBoundsException.class, () -> {
			 list.get(-3);
		  });
	}
	
	@Test
	public void clear() {
		list.add(5);
		list.add("Zvonko");
		list.add(2);
		
		assertEquals(3, list.size());
		
		list.clear();
		
		assertEquals(0, list.size());
	}
	
	@Test
	public void insert() {
		list.add(5);
		list.add("Zvonko");
		list.add(2);
		
		list.insert("Vlado", 1);
		list.insert(1, 0);
		list.insert("Želko", 5);
		
		assertEquals(6, list.size());
		assertEquals(1, list.get(0));
		assertEquals(5, list.get(1));
		assertTrue(list.get(2).equals("Vlado"));
		assertTrue(list.get(3).equals("Zvonko"));
		assertEquals(2, list.get(4));
		assertTrue(list.get(5).equals("Želko"));
	}
	
	@Test
	public void insertException() {
		list.add(1);
		list.add("Malnar");
		list.add("Zvonimir");
		
		assertThrows(IndexOutOfBoundsException.class, () -> {
			 list.insert(1, -2);
		  });
		
		assertThrows(IndexOutOfBoundsException.class, () -> {
			 list.insert(1, 7);
		  });
	}
	
	@Test
	public void contains() {
		list.add("Anica");
		list.add(42);
		
		assertTrue(list.contains(42));
		assertTrue(list.contains("Anica"));
		assertTrue(!list.contains(4));
	}
	
	@Test
	public void indexOf() {
		list.add(2);
		list.add("Luka");
		
		assertEquals(0, list.indexOf(2));
		assertEquals(-1, list.indexOf("Ana"));
		assertEquals(1, list.indexOf("Luka"));
	}
	
	@Test
	public void removeFirst() {
		list.add(2);
		list.add("Stanko");
		list.add(34);
		list.add("Braco");
		
		list.remove(0);
		
		assertTrue(list.get(0).equals("Stanko"));
		assertEquals(3, list.size());
	}
	
	@Test
	public void removeLast() {
		list.add(2);
		list.add("Stanko");
		list.add(34);
		list.add("Braco");
		
		list.remove(3);
		
		assertEquals(2, list.get(0));
		assertTrue(list.get(1).equals("Stanko"));
		assertEquals(34, list.get(2));
		assertEquals(3, list.size());
	}
	
	@Test
	public void removeMiddle() {
		list.add(2);
		list.add("Stanko");
		list.add(34);
		list.add("Braco");
		
		list.remove(1);
		
		assertEquals(2, list.get(0));
		assertEquals(34, list.get(1));
		assertTrue(list.get(2).equals("Braco"));
		assertEquals(3, list.size());
	}
	
	@Test
	public void constructorColection() {
		ArrayIndexedCollection other = new ArrayIndexedCollection();
		
		other.add(2);
		other.add("Stanko");
		other.add(34);
		
		LinkedListIndexedCollection collection = new LinkedListIndexedCollection(other);
		
		assertEquals(2, collection.get(0));
		assertTrue(collection.get(1).equals("Stanko"));
		assertEquals(34, collection.get(2));
		assertEquals(3, collection.size());
	}
	
	@Test
	public void toArray() {
		list.add(2);
		list.add("Stanko");
		list.add(34);
		list.add("Braco");
		
		assertEquals(2, list.toArray()[0]);
		assertTrue(list.toArray()[1].equals("Stanko"));
		assertEquals(34, list.toArray()[2]);
		assertTrue(list.toArray()[3].equals("Braco"));
		assertEquals(4, list.size());
		assertEquals(4, list.toArray().length);
	}

}
