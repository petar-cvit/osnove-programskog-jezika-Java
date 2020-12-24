package hr.fer.zemris.java.hw02;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;

class ComplexNumberTest {

	@Test
	public void construct() {
		ComplexNumber n = new ComplexNumber(2, 3);
		
		assertTrue(n.getReal()== 2);
		assertTrue(n.getImaginary()== 3);
	}
	
	@Test
	public void fromReal() {
		ComplexNumber n = ComplexNumber.fromReal(2);
		
		assertTrue(n.getReal()== 2);
		assertTrue(n.getImaginary()== 0);
	}
	
	@Test
	public void fromImaginary() {
		ComplexNumber n = ComplexNumber.fromImaginary(3);
		
		assertTrue(n.getReal()== 0);
		assertTrue(n.getImaginary()== 3);
	}
	
	@Test
	public void parse1() {
		ComplexNumber a = ComplexNumber.parse("3.4");
		ComplexNumber b = ComplexNumber.parse("3.2i");
		ComplexNumber c = ComplexNumber.parse("-4.1");
		ComplexNumber d = ComplexNumber.parse("-5.8i");
		ComplexNumber e = ComplexNumber.parse("3.6+4.11i");
		ComplexNumber f = ComplexNumber.parse("2.08-5.34i");
		ComplexNumber g = ComplexNumber.parse("-6.15+7.87i");
		ComplexNumber h = ComplexNumber.parse("-9.06-4.55i");
		ComplexNumber i = ComplexNumber.parse("+2.8");
		ComplexNumber j = ComplexNumber.parse("+5.23+4.5i");
		ComplexNumber k = ComplexNumber.parse("+9.08-9.99i");
		ComplexNumber l = ComplexNumber.parse("-i");
		
		assertTrue(a.getReal() == 3.4);
		assertTrue(a.getImaginary() == 0);
		
		assertTrue(b.getReal() == 0);
		assertTrue(b.getImaginary() == 3.2);
		
		assertTrue(c.getReal() == -4.1);
		assertTrue(c.getImaginary() == 0);
		
		assertTrue(d.getReal() == 0);
		assertTrue(d.getImaginary() == -5.8);
		
		assertTrue(e.getReal() == 3.6);
		assertTrue(e.getImaginary() == 4.11);
		
		assertTrue(f.getReal() == 2.08);
		assertTrue(f.getImaginary() == -5.34);
		
		assertTrue(g.getReal() == -6.15);
		assertTrue(g.getImaginary() == 7.87);
		
		assertTrue(h.getReal() == -9.06);
		assertTrue(h.getImaginary() == -4.55);
		
		assertTrue(i.getReal() == 2.8);
		assertTrue(i.getImaginary() == 0);
		
		assertTrue(j.getReal() == 5.23);
		assertTrue(j.getImaginary() == 4.5);
		
		assertTrue(k.getReal() == 9.08);
		assertTrue(k.getImaginary() == -9.99);
		
		assertTrue(l.getReal() == 0);
		assertTrue(l.getImaginary() == -1);
	}
	
	@Test
	public void parseExc1() {
		assertThrows(NumberFormatException.class, () -> {
		    ComplexNumber.parse("-i317");
		  });
	}
	
	@Test
	public void parseExc2() {
		assertThrows(NumberFormatException.class, () -> {
		    ComplexNumber.parse("-2.71+-3.15i");
		  });
	}
	
	@Test
	public void parseExc3() {
		assertThrows(NumberFormatException.class, () -> {
		    ComplexNumber.parse("--2.71");
		  });
	}
	
	@Test
	public void parseExc4() {
		assertThrows(NumberFormatException.class, () -> {
		    ComplexNumber.parse("-+2.71");
		  });
	}
	
	@Test
	public void parseExc5() {
		assertThrows(NumberFormatException.class, () -> {
		    ComplexNumber.parse("+-2.71");
		  });
	}
	
	@Test
	public void parseExc6() {
		assertThrows(NumberFormatException.class, () -> {
		    ComplexNumber.parse("3.14-+2.71i");
		  });
	}
	
	@Test
	public void getMagnitude() {
		ComplexNumber a = new ComplexNumber(3, 4);
		
		assertTrue(Math.abs(a.getMagnitude() - 5) < 10E-4);
	}
	
	@Test
	public void getAngle() {
		ComplexNumber a = new ComplexNumber(3, 4);
		ComplexNumber b = new ComplexNumber(-2, 5);
		ComplexNumber c = new ComplexNumber(5, -7);
		ComplexNumber d = new ComplexNumber(-2, -4);
		
		assertTrue(Math.abs(a.getAngle() - 0.927295) < 10E-4);
		assertTrue(Math.abs(b.getAngle() - 1.951302) < 10E-4);
		assertTrue(Math.abs(c.getAngle() - 5.332639) < 10E-4);
		assertTrue(Math.abs(d.getAngle() - 4.248742) < 10E-4);
	}
	
	@Test
	public void add() {
		ComplexNumber a = new ComplexNumber(2.1, 3.4);
		
		ComplexNumber b = a.add(new ComplexNumber(4.6, 6.3));
		
		assertTrue(Math.abs(b.getReal() - 6.7) < 10E-4);
		assertTrue(Math.abs(b.getImaginary() - 9.7) < 10E-4);
	}
	
	@Test
	public void sub() {
		ComplexNumber a = new ComplexNumber(9.08, 4.67);
		
		ComplexNumber b = a.sub(new ComplexNumber(5.34, 7.90));

		assertTrue(Math.abs(b.getReal() - 3.74) < 10E-4);
		assertTrue(Math.abs(b.getImaginary() + 3.23) < 10E-4);
	}
	
	@Test
	public void mul() {
		ComplexNumber a = new ComplexNumber(-3.45, 2.91);
		
		ComplexNumber b = a.mul(new ComplexNumber(6.98, -2.43));

		assertTrue(Math.abs(b.getReal() + 17.0097) < 10E-4);
		assertTrue(Math.abs(b.getImaginary() - 28.6953) < 10E-4);
	}
	
	@Test
	public void div() {
		ComplexNumber a = new ComplexNumber(4.46, -2.34);
		
		ComplexNumber b = a.div(new ComplexNumber(-7.37, 10.98));

		assertTrue(Math.abs(b.getReal() + 0.334882) < 10E-4);
		assertTrue(Math.abs(b.getImaginary() + 0.181412) < 10E-4);
	}
	
	@Test
	public void power() {
		ComplexNumber a = new ComplexNumber(-5.26, -1.89);
		
		ComplexNumber b = a.power(3);

		assertTrue(Math.abs(b.getReal() + 89.163838) < 10E-4);
		assertTrue(Math.abs(b.getImaginary() + 150.124023) < 10E-4);
	}
	
	@Test
	public void root() {
		ComplexNumber a = new ComplexNumber(5.89, -3.42);
		
		ComplexNumber[] array = a.root(4);
		
		assertTrue(Math.abs(array[0].getReal() - 0.21185) < 10E-4);
		assertTrue(Math.abs(array[0].getImaginary() - 1.60152) < 10E-4);
		
		assertTrue(Math.abs(array[1].getReal() + 1.60152) < 10E-4);
		assertTrue(Math.abs(array[1].getImaginary() - 0.21185) < 10E-4);
		
		assertTrue(Math.abs(array[2].getReal() + 0.21185) < 10E-4);
		assertTrue(Math.abs(array[2].getImaginary() + 1.60152) < 10E-4);
		
		assertTrue(Math.abs(array[3].getReal() - 1.60152) < 10E-4);
		assertTrue(Math.abs(array[3].getImaginary() + 0.21185) < 10E-4);
	}

}
