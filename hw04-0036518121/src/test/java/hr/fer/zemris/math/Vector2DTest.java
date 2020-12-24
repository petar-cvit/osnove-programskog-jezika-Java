package hr.fer.zemris.math;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class Vector2DTest {

	@Test
	public void constructorTest() {
		Vector2D vector = new Vector2D(4, 4);
		
		assertNotNull(vector);
	}
	
	@Test
	public void getTest() {
		Vector2D v = new Vector2D(-3, 9.2);
		
		assertEquals(-3, v.getX());
		assertEquals(9.2, v.getY());
	}
	
	@Test
	public void rotateTest() {
		Vector2D v = new Vector2D(3, 3);
		
		v.rotate(Math.PI / 2);
		
		assertTrue(Math.abs(-3 - v.getX()) < 10E-4);
		assertTrue(Math.abs(3 - v.getY()) < 10E-4);
		
		v.rotate(Math.PI / 2);
		
		assertTrue(Math.abs(-3 - v.getX()) < 10E-4);
		assertTrue(Math.abs(-3 - v.getY()) < 10E-4);
		
		v.rotate(Math.PI / 2);
		
		assertTrue(Math.abs(3 - v.getX()) < 10E-4);
		assertTrue(Math.abs(-3 - v.getY()) < 10E-4);
		
		v.rotate(Math.PI / 2);
	
		assertTrue(Math.abs(3 - v.getX()) < 10E-4);
		assertTrue(Math.abs(3 - v.getY()) < 10E-4);
	
	}
	
	@Test
	public void rotatedTest() {
		Vector2D v = new Vector2D(-2, 5);
		
		Vector2D v2 = v.rotated(-Math.PI/2);
		
		assertTrue(Math.abs(5 - v2.getX()) < 10E-4);
		assertTrue(Math.abs(2 - v2.getY()) < 10E-4);
		
		Vector2D v3 = v2.rotated(-Math.PI/2);
		
		assertTrue(Math.abs(2 - v3.getX()) < 10E-4);
		assertTrue(Math.abs(-5 - v3.getY()) < 10E-4);
		
		Vector2D v4 = v3.rotated(-Math.PI/2);
		
		assertTrue(Math.abs(-5 - v4.getX()) < 10E-4);
		assertTrue(Math.abs(-2 - v4.getY()) < 10E-4);
		
		Vector2D v5 = v4.rotated(-Math.PI/2);
		
		assertTrue(Math.abs(-2 - v5.getX()) < 10E-4);
		assertTrue(Math.abs(5 - v5.getY()) < 10E-4);
	}
	
	@Test
	public void scaleTest1() {
		Vector2D v= new Vector2D(2.4, 9.8);
		
		Vector2D v2 = v.scaled(3.2);
		
		assertTrue(Math.abs(7.68 - v2.getX()) < 10E-4);
		assertTrue(Math.abs(31.36 - v2.getY()) < 10E-4);
		
		Vector2D v3 = v.scaled(-3.2);
		
		assertTrue(Math.abs(-7.68 - v3.getX()) < 10E-4);
		assertTrue(Math.abs(-31.36 - v3.getY()) < 10E-4);
	}
	
	@Test
	public void scaleTest2() {
		Vector2D v= new Vector2D(4.6, -8.3);
		
		Vector2D v2 = v.scaled(4.4);
		
		assertTrue(Math.abs(20.24 - v2.getX()) < 10E-4);
		assertTrue(Math.abs(-36.52 - v2.getY()) < 10E-4);
		
		Vector2D v3 = v.scaled(-4.4);
		
		assertTrue(Math.abs(-20.24 - v3.getX()) < 10E-4);
		assertTrue(Math.abs(36.52 - v3.getY()) < 10E-4);
	}
	
	@Test
	public void scaleTest3() {
		Vector2D v= new Vector2D(-1.9, 0.3);
		
		Vector2D v2 = v.scaled(9.2);
		
		assertTrue(Math.abs(-17.48 - v2.getX()) < 10E-4);
		assertTrue(Math.abs(2.76 - v2.getY()) < 10E-4);
		
		Vector2D v3 = v.scaled(-9.2);
		
		assertTrue(Math.abs(17.48 - v3.getX()) < 10E-4);
		assertTrue(Math.abs(-2.76 - v3.getY()) < 10E-4);
	}
	
	@Test
	public void scaleTest4() {
		Vector2D v= new Vector2D(-2.3, -5.4);
		
		Vector2D v2 = v.scaled(3.1);
		
		assertTrue(Math.abs(-7.13 - v2.getX()) < 10E-4);
		assertTrue(Math.abs(-16.74 - v2.getY()) < 10E-4);
		
		Vector2D v3 = v.scaled(-3.1);
		
		assertTrue(Math.abs(7.13 - v3.getX()) < 10E-4);
		assertTrue(Math.abs(16.74 - v3.getY()) < 10E-4);
	}
	
	@Test
	public void translateTest() {
		Vector2D v = new Vector2D(1, 3);
		
		Vector2D v2 = v.translated(new Vector2D(2, 2));
		
		assertEquals(3, v2.getX());
		assertEquals(5, v2.getY());
	}
}















