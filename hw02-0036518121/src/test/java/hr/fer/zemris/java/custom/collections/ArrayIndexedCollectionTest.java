package hr.fer.zemris.java.custom.collections;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import hr.fer.zemris.java.hw02.ComplexNumber;

class ArrayIndexedCollectionTest {
	
	ArrayIndexedCollection collection = new ArrayIndexedCollection();

	@Test
	public void defaultContructor() {
		ArrayIndexedCollection newCol = new ArrayIndexedCollection();
		
		assertTrue(newCol.isEmpty());
	}
	
	@Test
	public void constructorWithCollection() {
		collection.add(1);
		collection.add(2);
		collection.add(3);
		
		ArrayIndexedCollection newCol = new ArrayIndexedCollection(collection);
		
		assertEquals(1, newCol.toArray()[0]);
		assertEquals(2, newCol.toArray()[1]);
		assertEquals(3, newCol.toArray()[2]);
	}
	
	@Test
	public void constructorException() {
		assertThrows(NullPointerException.class, () -> {
		     new ArrayIndexedCollection(null);
		  });
	}
	
	@Test
	public void constructorExceptionWithCapacity() {
		assertThrows(NullPointerException.class, () -> {
		     new ArrayIndexedCollection(null, 5);
		  });
	}
	
	@Test
	public void constructorCollectionSize() {
		collection.add("braco");
		collection.add(2);
		collection.add("laki");
		collection.add(4);
		
		ArrayIndexedCollection newCol = new ArrayIndexedCollection(collection, 3);
		
		assertEquals(4, newCol.size());
		assertTrue("braco".equals(newCol.get(0)));
		assertEquals(2, newCol.get(1));
		assertTrue("laki".equals(newCol.get(2)));
		assertEquals(4,  newCol.get(3));
	}
	
	@Test
	public void add() {
		collection.add(52);
	    collection.add("Jure");

	    Object[] array = collection.toArray();

	    
	    assertEquals(52,array[0]);
	    assertTrue("Jure".equals(array[1]));
	}
	
	@Test
	public void addException() {
		assertThrows(NullPointerException.class, () -> {
		     collection.add(null);;
		  });
	}
	 
	@Test
	public void addAll() {
		ArrayIndexedCollection newCol = new ArrayIndexedCollection();
		newCol.add(1);
		newCol.add(2);
		newCol.add(3);
		
		collection.addAll(newCol);
		
		assertEquals(3, collection.size());
		assertEquals(1, collection.toArray()[0]);
		assertEquals(2, collection.toArray()[1]);
		assertEquals(3, collection.toArray()[2]);
	}
	
	@Test
	public void get() {
		collection.add(1);
		collection.add("Malnar");
		collection.add("Zvonimir");
		
		assertEquals(1, collection.get(0));
		assertTrue("Malnar".equals(collection.get(1)));
		assertTrue("Zvonimir".equals(collection.get(2)));
	}
	
	@Test
	public void getException() {
		collection.add(3);
		collection.add("škola");
		
		assertThrows(IndexOutOfBoundsException.class, () -> {
		     collection.get(2);
		  });
		assertThrows(IndexOutOfBoundsException.class, () -> {
			 collection.get(-3);
		  });
	}
	
	@Test
	public void clear() {
		collection.add(1);
		collection.add("Malnar");
		collection.add("Zvonimir");
		
		collection.clear();
		
		assertEquals(0, collection.size());
	}
	
	@Test
	public void insert() {
		collection.add(1);
		collection.add("Malnar");
		collection.add("Zvonimir");
		
		collection.insert("Želko", 2);
		collection.insert(4, collection.size());
		collection.insert(9, 0);
		
		assertEquals(9, collection.get(0));
		assertEquals(1, collection.get(1));
		assertTrue("Malnar".equals(collection.get(2)));
		assertTrue("Želko".equals(collection.get(3)));
		assertTrue("Zvonimir".equals(collection.get(4)));
		assertEquals(4, collection.get(5));
	}
	
	@Test
	public void insertException() {
		collection.add(1);
		collection.add("Malnar");
		collection.add("Zvonimir");
		
		assertThrows(IndexOutOfBoundsException.class, () -> {
			 collection.insert(1, -2);
		  });
		
		assertThrows(IndexOutOfBoundsException.class, () -> {
			 collection.insert(1, 7);
		  });
	}
	
	@Test
	public void indexOf() {
		collection.add("Vlado");
		collection.add(2);
		
		assertEquals(1, collection.indexOf(2));
		assertEquals(-1, collection.indexOf("Braco"));
	}
	
	@Test
	public void remove() {
		collection.add("želko");
		collection.add("vlado");
		collection.add("braco");
		
		collection.remove(1);
		
		assertEquals(2, collection.size());
		assertTrue("želko".equals(collection.get(0)));
		assertTrue("braco".equals(collection.get(1)));
		
	}
	
	@Test
	public void removeException() {
		collection.add(3);
		collection.add("škola");
		
		assertThrows(IndexOutOfBoundsException.class, () -> {
			 collection.remove(-2);
		  });
		
		assertThrows(IndexOutOfBoundsException.class, () -> {
			 collection.remove(7);
		  });
		
		assertEquals(2, collection.size());
	}
	
	@Test
	public void contains() {
		collection.add(13);
		collection.add(23);
		collection.add(33);
		
		assertTrue(collection.contains(23));
		assertTrue(!collection.contains("Malnar"));
	}

}
