package hr.fer.zemris.java.hw05.db;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class FieldValueGettersTest {
	
	@Test
	void test1() {
		StudentRecord r = new StudentRecord("003", "Barbarić", "Mario", 3);
		FieldValueGetters getter = new FieldValueGetters();
		
		assertEquals("003", getter.JMBAG.get(r));
		assertEquals("Mario", getter.FIRST_NAME.get(r));
		assertEquals("Barbarić", getter.LAST_NAME.get(r));
	}

}
