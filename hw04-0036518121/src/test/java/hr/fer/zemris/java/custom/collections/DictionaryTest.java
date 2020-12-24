package hr.fer.zemris.java.custom.collections;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;

class DictionaryTest {

	@Test
	public void constructorTest() {
		Dictionary<String, String> dict = new Dictionary<String, String>();
		
		assertTrue(dict.isEmpty());
		assertEquals(0, dict.size());
	}
	
	@Test
	public void getTest() {
		Dictionary<String, Double> dict = new Dictionary<String, Double>();
		
		dict.put("Marica", 9.3);
		dict.put("Luka", -3.);
		
		assertNull(dict.get("Maja"));
		assertEquals(-3, dict.get("Luka"));
		assertEquals(9.3, dict.get("Marica"));
		
		assertFalse(dict.isEmpty());
	}
	
	@Test
	public void putTest() {
		Dictionary<String, Integer> dict = new Dictionary<String, Integer>();
		
		dict.put("Štefica", 4);
		dict.put("Ksenija", -1);
		dict.put("avion", 12);
		dict.put("mobitel", 0);
		
		assertEquals(4, dict.size());
		
		assertEquals(4, dict.get("Štefica"));
		assertEquals(-1, dict.get("Ksenija"));
		assertEquals(12, dict.get("avion"));
		assertEquals(0, dict.get("mobitel"));
		
	}
	

	@Test
	public void clearTest() {
		Dictionary<String, Integer> dict = new Dictionary<String, Integer>();
		
		assertEquals(0, dict.size());
		
		dict.put("Štefica", 4);
		dict.put("Ksenija", -1);
		dict.put("avion", 12);
		dict.put("mobitel", 0);
		
		assertEquals(4, dict.size());
		assertFalse(dict.isEmpty());
		
		dict.clear();
		
		assertEquals(0, dict.size());
		
		assertNull(dict.get("Štefica"));
		assertNull(dict.get("Ksenija"));
		assertNull(dict.get("avion"));
		assertNull(dict.get("mobitel"));
		assertTrue(dict.isEmpty());
	}
	
	@Test
	public void writeOverTest() {
		Dictionary<Integer, String> dict = new Dictionary<Integer, String>();
		
		dict.put(12, "Strina");
		dict.put(10, "Anica");
		dict.put(8, "Željko");
		dict.put(10, "Braco");
		
		assertEquals(3, dict.size());
		assertFalse(dict.isEmpty());
		
		assertTrue(dict.get(12).equals("Strina"));
		assertTrue(dict.get(10).equals("Braco"));
		assertTrue(dict.get(8).equals("Željko"));
	}
}
