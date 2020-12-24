package hr.fer.zemris.java.hw05.db;

import static org.junit.jupiter.api.Assertions.*;

import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class StudentDastabaseTest {
	
	@Test
	void constructorTest() {
		List<String> l = new ArrayList<String>();
		
		l.add("003 Laknar Luka 3");
		l.add("002  Barbarić	Mario 2");
		
		StudentDatabase db = new StudentDatabase(l);
		
		assertEquals(new StudentRecord("003", "Laknar", "Luka", 3), db.forJMBAG("003"));
		assertEquals(new StudentRecord("002", "Barbarić", "Mario", 2), db.forJMBAG("002"));
	}
	
	@Test
	void constructorExc1() {
		List<String> l = new ArrayList<String>();
		
		l.add("003 Laknar Luka 3");
		l.add("002 4  Barbarić	Mario 2");
		
		assertThrows(IllegalArgumentException.class, () -> {
		    new StudentDatabase(l);
		  });
		
	}
	
	@Test
	void constructorExc2() {
		List<String> l = new ArrayList<String>();
		
		l.add("003 Laknar Luka 3");
		l.add("002 Mario 2");
		
		assertThrows(IllegalArgumentException.class, () -> {
		    new StudentDatabase(l);
		  });
		
	}
	
	@Test
	void constructorExc3() {
		List<String> l = new ArrayList<String>();
		
		l.add("003 Laknar Luka 3");
		l.add("002 Barbaric Mario 4 2");
		
		assertThrows(IllegalArgumentException.class, () -> {
		    new StudentDatabase(l);
		  });
		
	}
	
	@Test
	void constructorExc4() {
		List<String> l = new ArrayList<String>();
		
		l.add("003 Laknar Luka 3");
		l.add("002 Barbaric Mario Luka Jakov 2");
		
		assertThrows(IllegalArgumentException.class, () -> {
		    new StudentDatabase(l);
		  });
		
	}

	@Test
	void forJMBAGTest() {
		StudentDB db = new StudentDB();
		
		StudentDatabase database = db.load("src/main/resources/database.txt");
		
		assertEquals(63, database.filter(r -> true).size());
		assertEquals(0, database.filter(r -> false).size());
	}

}
