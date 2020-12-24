package hr.fer.zemris.java.hw05.db;

import static org.junit.jupiter.api.Assertions.*;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class QueryParserTest {

	@Test
	void test1() {
		QueryParser qp1 = new QueryParser(" jmbag  =\"0123456789\"");
		assertTrue(qp1.isDirectQuery());
		assertEquals("0123456789", qp1.getQueriedJMBAG());
		assertEquals(1, qp1.getQuery().size());
		
	}
	
	@Test
	void test2() {
		QueryParser qp1 = new QueryParser(" jmbag  =\"0123456789\" and firstName LIKE \"*a\"");
		assertFalse(qp1.isDirectQuery());
		assertThrows(IllegalArgumentException.class, () -> 
				qp1.getQueriedJMBAG());
		assertEquals(2, qp1.getQuery().size());
		
	}
	
	@Test
	void test3() throws IOException {
		QueryParser qp = new QueryParser("lastName LIKE \"M*\" and jmbag<=\"0000000036\" ");
		
		List<String> lines = Files.readAllLines(
				 Paths.get("./src/main/resources/database.txt"),
				 StandardCharsets.UTF_8
				);
		
		StudentDatabase db = new StudentDatabase(lines);
		
		List<StudentRecord> records = db.filter(new QueryFilter(qp.getQuery()));
		
		assertEquals(new StudentRecord("0000000033", "Machiedo", "Ivor", 2), records.get(0));
		assertEquals(new StudentRecord("0000000034", "Majić", "Diana", 3), records.get(1));
		assertEquals(new StudentRecord("0000000035", "Marić", "Ivan", 4), records.get(2));
		assertEquals(new StudentRecord("0000000036", "Markić", "Ante", 5), records.get(3));
	}

}
