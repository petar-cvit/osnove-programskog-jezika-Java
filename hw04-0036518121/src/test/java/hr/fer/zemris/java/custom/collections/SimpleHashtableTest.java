package hr.fer.zemris.java.custom.collections;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;

import hr.fer.zemris.java.custom.collections.SimpleHashtable.TableEntry;

import java.util.ConcurrentModificationException;
import java.util.Iterator;

class SimpleHashtableTest {

	@Test
	public void constructorTest() {
		assertThrows(IllegalArgumentException.class, () -> new SimpleHashtable<Integer, Integer>(0));
		SimpleHashtable<Integer, String> hashTable = new SimpleHashtable<Integer, String>(4);
		
		assertTrue(hashTable.isEmpty());
	}
	
	@Test
	public void putTest() {
		SimpleHashtable<Integer, String> hashTable = new SimpleHashtable<Integer, String>(3);
		
		hashTable.put(3, "Anica");
		hashTable.put(2, "Željko");
		hashTable.put(-4, "Braco");
		assertThrows(NullPointerException.class, () -> hashTable.put(null, "Ivan"));
		hashTable.put(1, null);
		hashTable.put(3, "Luka");
		
		assertEquals(4, hashTable.size());
		
		assertEquals(null, hashTable.get(1));
		assertEquals("Željko", hashTable.get(2));
		assertEquals("Luka", hashTable.get(3));
		assertEquals("Braco", hashTable.get(-4));
		assertNull(hashTable.get(5));
		assertNull(hashTable.get(null));
		assertNull(hashTable.get("Mirko"));
	}
	
	
	@Test
	public void removeTest() {
		SimpleHashtable<Integer, String> hashTable = new SimpleHashtable<Integer, String>(2);
		
		hashTable.put(3, "Anica");
		hashTable.put(2, "Željko");
		hashTable.put(-4, "Braco");
		hashTable.put(1, null);
		hashTable.put(3, "Luka");
		
		assertEquals(4, hashTable.size());
		
		hashTable.remove(null);
		assertEquals(4, hashTable.size());
		
		hashTable.remove(5);
		assertEquals(4, hashTable.size());
		

		hashTable.remove("Luka");
		assertEquals(4, hashTable.size());
		

		hashTable.remove(3);
		assertEquals(3, hashTable.size());
		

		hashTable.remove(2);
		assertEquals(2, hashTable.size());
	}
	
	@Test
	public void containsTest() {
		SimpleHashtable<String, Integer> hashTable = new SimpleHashtable<String, Integer>(2);
		
		hashTable.put("Marko", 2);
		hashTable.put("Anica", 5);
		hashTable.put("Mirko", 4);
		hashTable.put("Luka", -12);
		
		assertTrue(hashTable.containsKey("Marko"));
		assertTrue(hashTable.containsKey("Anica"));
		assertTrue(hashTable.containsKey("Mirko"));
		assertTrue(hashTable.containsKey("Luka"));
		assertFalse(hashTable.containsKey(null));
		assertFalse(hashTable.containsKey("Zdravko"));
		assertFalse(hashTable.containsKey(3));
		
		assertTrue(hashTable.containsValue(2));
		assertTrue(hashTable.containsValue(5));
		assertTrue(hashTable.containsValue(4));
		assertTrue(hashTable.containsValue(-12));
		assertFalse(hashTable.containsValue(null));
		assertFalse(hashTable.containsValue("Zdravko"));
		assertFalse(hashTable.containsValue(3));
	}
	
	@Test
	public void iteratorTest() {
		SimpleHashtable<String, Integer> hashTable = new SimpleHashtable<String, Integer>(2);
		
		hashTable.put("Marko", 2);
		hashTable.put("Anica", 5);
		hashTable.put("Mirko", 4);
		hashTable.put("Luka", -12);
		
		String compare = "";
		
		for(SimpleHashtable.TableEntry<String,Integer> pair1 : hashTable) {
			compare = compare.concat(pair1 + " ");
		}
		
		assertEquals("Marko=2 Anica=5 Mirko=4 Luka=-12 ", compare);

	}

	@Test
	public void iteratorTest2() {
		SimpleHashtable<String, Integer> hashTable = new SimpleHashtable<String, Integer>(2);
		
		hashTable.put("Marko", 2);
		hashTable.put("Anica", 5);
		hashTable.put("Luka", -12);
		
		String compare = "";
		
		
		for(SimpleHashtable.TableEntry<String,Integer> pair1 : hashTable) {
			for(SimpleHashtable.TableEntry<String,Integer> pair2 : hashTable) {
				compare = compare.concat(pair1 + "-" + pair2+ "\r\n");
			}		
		}
		
		String correct = "Marko=2-Marko=2\r\n" + 
				"Marko=2-Anica=5\r\n" + 
				"Marko=2-Luka=-12\r\n" + 
				"Anica=5-Marko=2\r\n" + 
				"Anica=5-Anica=5\r\n" + 
				"Anica=5-Luka=-12\r\n" + 
				"Luka=-12-Marko=2\r\n" + 
				"Luka=-12-Anica=5\r\n" + 
				"Luka=-12-Luka=-12\r\n";
		
		assertEquals(compare, correct);
		
	}
	
	@Test
	public void iteratorTest3() {
		SimpleHashtable<Integer, String> hashTable = new SimpleHashtable<Integer, String>(2);
		
		hashTable.put(3, "Anica");
		hashTable.put(2, "Željko");
		hashTable.put(-4, "Braco");
		hashTable.put(1, null);
		hashTable.put(8, "Luka");
		
		Iterator<TableEntry<Integer, String>> iter = hashTable.iterator();
		
		assertEquals(5, hashTable.size());
		
		iter.next();
		iter.next();
		iter.remove();
		iter.next();

		assertEquals(4, hashTable.size());
	}

	@Test
	public void iteratorTest4() {
		SimpleHashtable<Integer, String> hashTable = new SimpleHashtable<Integer, String>(2);
		
		hashTable.put(3, "Anica");
		hashTable.put(2, "Željko");
		hashTable.put(-4, "Braco");
		hashTable.put(1, null);
		hashTable.put(8, "Luka");
		
		Iterator<TableEntry<Integer, String>> iter = hashTable.iterator();
		
		assertEquals(5, hashTable.size());
		
		iter.next();
		iter.next();
		iter.remove();
		assertThrows(IllegalStateException.class, () -> iter.remove());
		iter.next();

		assertEquals(4, hashTable.size());
	}
	
	@Test
	public void iteratorTest5() {
		SimpleHashtable<Integer, String> hashTable = new SimpleHashtable<Integer, String>(2);
		
		hashTable.put(3, "Anica");
		hashTable.put(2, "Željko");
		hashTable.put(-4, "Braco");
		hashTable.put(1, null);
		hashTable.put(8, "Luka");
		
		Iterator<TableEntry<Integer, String>> iter = hashTable.iterator();
		
		assertEquals(5, hashTable.size());
		
		iter.next();
		iter.next();
		hashTable.put(5, "test");
		assertThrows(ConcurrentModificationException.class, () -> iter.next());
	}
	
	@Test
	public void iteratorTest6() {
		SimpleHashtable<Integer, String> hashTable = new SimpleHashtable<Integer, String>(2);
		
		hashTable.put(3, "Anica");
		hashTable.put(2, "Željko");
		hashTable.put(-4, "Braco");
		hashTable.put(1, null);
		hashTable.put(8, "Luka");
		
		Iterator<TableEntry<Integer, String>> iter = hashTable.iterator();
		
		assertEquals(5, hashTable.size());
		
		iter.next();
		iter.next();
		hashTable.remove(2);
		assertThrows(ConcurrentModificationException.class, () -> iter.next());
	}
	
}

























