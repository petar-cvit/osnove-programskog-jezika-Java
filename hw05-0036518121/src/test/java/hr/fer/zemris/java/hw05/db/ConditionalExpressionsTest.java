package hr.fer.zemris.java.hw05.db;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class ConditionalExpressionsTest {

	@Test
	void test() {
		IComparisonOperator oper = ComparisonOperators.GREATER_OR_EQUALS;
		String literal = "Sven";
		IFieldValueGetter getter = FieldValueGetters.LAST_NAME;
		
		ConditionalExpressions ce = new ConditionalExpressions(getter, literal, oper);
		
		assertEquals(ComparisonOperators.GREATER_OR_EQUALS, ce.getComparisonOperator());
		assertEquals("Sven", ce.getStringLiteral());
		assertEquals(FieldValueGetters.LAST_NAME, ce.getFieldGetter());
	}

}
