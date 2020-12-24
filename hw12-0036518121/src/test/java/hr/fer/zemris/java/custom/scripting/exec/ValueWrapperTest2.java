package hr.fer.zemris.java.custom.scripting.exec;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;

class ValueWrapperTest2 {

	@Test
	void test1() {
		ValueWrapper v1 = new ValueWrapper(null);
		ValueWrapper v2 = new ValueWrapper(null);
		v1.add(v2.getValue()); 
		
		assertEquals(0, v1.getValue());
	}
	
	@Test
	void test2() {
		ValueWrapper v1 = new ValueWrapper("1.2E1");
		ValueWrapper v2 = new ValueWrapper(Integer.valueOf(1));
		v1.add(v2.getValue()); 
		
		assertEquals(13.0, v1.getValue());
	}
	
	@Test
	void test3() {
		ValueWrapper v1 = new ValueWrapper("Ankica");
		ValueWrapper v2 = new ValueWrapper(Integer.valueOf(1));
		
		assertThrows(RuntimeException.class, () -> v1.add(v2.getValue()));
	}
	
	@Test
	void test4() {
		ValueWrapper v1 = new ValueWrapper("2");
		ValueWrapper v2 = new ValueWrapper("true");
		
		assertThrows(RuntimeException.class, () -> v1.add(v2.getValue()));
	}
	
	@Test
	void test5() {
		ValueWrapper v1 = new ValueWrapper(2.0);
		ValueWrapper v2 = new ValueWrapper(Integer.valueOf(1));
		
		assertEquals(1, v1.numCompare(v2.getValue()));
	}

}
