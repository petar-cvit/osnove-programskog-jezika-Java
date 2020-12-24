package hr.fer.zemris.java.hw05.db;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class ComparisonOperatorsTest {

	@Test
	void testLike() {
		IComparisonOperator oper = ComparisonOperators.LIKE;
		assertFalse(oper.satisfied("gobnob", "Aba"));
		assertFalse(oper.satisfied("AAA", "AA*AA"));
		assertTrue(oper.satisfied("AAAA", "AA*AA"));
		assertTrue(oper.satisfied("Braco", "B*"));
		assertTrue(oper.satisfied("Braco", "*o"));
		assertTrue(oper.satisfied("Braco", "Bra*co"));
		assertFalse(oper.satisfied("Braco", "Bra*aco"));
	}
	
	@Test
	void testEquals() {
		IComparisonOperator oper = ComparisonOperators.EQUALS;
		
		assertTrue(oper.satisfied("Ana", "Ana"));
		assertFalse(oper.satisfied("Ana", "Anam"));
	}
	
	@Test
	void testNotEquals() {
		IComparisonOperator oper = ComparisonOperators.NOT_EQUALS;
		
		assertFalse(oper.satisfied("Ana", "Ana"));
		assertTrue(oper.satisfied("Ana", "Anam"));
	}
	
	@Test
	void testLess() {
		IComparisonOperator oper = ComparisonOperators.LESS;
		
		assertTrue(oper.satisfied("Ana", "Banana"));
		assertFalse(oper.satisfied("Ana", "Ana"));
	}
	
	@Test
	void testGreater() {
		IComparisonOperator oper = ComparisonOperators.GREATER;
		
		assertTrue(oper.satisfied("Banana", "Ana"));
		assertFalse(oper.satisfied("Ana", "Ana"));
	}
	
	@Test
	void testLessOrEquals() {
		IComparisonOperator oper = ComparisonOperators.LESS_OR_EQUALS;
		
		assertTrue(oper.satisfied("Ana", "Banana"));
		assertTrue(oper.satisfied("Ana", "Ana"));
		assertFalse(oper.satisfied("Ana", "A"));
	}
	
	@Test
	void testGreaterOrEquals() {
		IComparisonOperator oper = ComparisonOperators.GREATER_OR_EQUALS;
		
		assertTrue(oper.satisfied("Banana", "Ana"));
		assertTrue(oper.satisfied("Ana", "Ana"));
		assertFalse(oper.satisfied("A", "Ana"));
	}

}
