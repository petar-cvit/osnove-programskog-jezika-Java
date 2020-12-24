package hr.fer.zemris.lsystems.impl;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import hr.fer.zemris.java.custom.collections.Dictionary;

class LSystemBuilderImplTest {

	@Test
	public void generate0Test() {
		
		LSystemBuilderImpl test = new LSystemBuilderImpl();
		test.registerProduction('F', "F+F--F+F").setAxiom("F");
		
		assertEquals("F", test.build().generate(0));
	}
	
	@Test
	public void generate1Test() {
		
		LSystemBuilderImpl test = new LSystemBuilderImpl();
		test.registerProduction('F', "F+F--F+F").setAxiom("F");
		
		assertEquals("F+F--F+F", test.build().generate(1));
	}
	
	@Test
	public void generate2Test() {
		
		LSystemBuilderImpl test = new LSystemBuilderImpl();
		test.registerProduction('F', "F+F--F+F").setAxiom("F");
		
		assertEquals("F+F--F+F+F+F--F+F--F+F--F+F+F+F--F+F", test.build().generate(2));
	}

}
